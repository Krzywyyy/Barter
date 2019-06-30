package pl.krzywyyy.barter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private static final String HEADER_STRING = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String SECRET = "SecretKey";
	private static final long EXPIRATION_TIME = 364_000_000;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(UserService userService, AuthenticationManager authenticationManager)
	{
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
	                                            HttpServletResponse response) throws AuthenticationException
	{
		try{
			User credentials = new ObjectMapper().readValue(request.getInputStream(),User.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getLogin(),
					credentials.getPassword(),
					new ArrayList<>()
			));
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
	                                        HttpServletResponse response,
	                                        FilterChain chain,
	                                        Authentication authResult) throws IOException
	{
		String token = JWT.create()
				.withSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SECRET.getBytes()));
		response.addHeader(HEADER_STRING,TOKEN_PREFIX + token);
		response.setStatus(204);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.flush();
	}
}
