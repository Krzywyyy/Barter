package pl.krzywyyy.barter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.krzywyyy.barter.users.UserService;
import pl.krzywyyy.barter.users.UserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class Authorization extends BasicAuthenticationFilter {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "SecretKey";

    private final UserServiceImpl userService;

    public Authorization(AuthenticationManager authenticationManager, UserServiceImpl userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_STRING);

        if (authorizationHeader != null) {
            String userEmail = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(authorizationHeader.replace(TOKEN_PREFIX, "")).getSubject();
            if (userEmail != null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmail);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
