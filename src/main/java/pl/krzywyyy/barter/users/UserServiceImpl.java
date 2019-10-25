package pl.krzywyyy.barter.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.utils.exceptions.AlreadyExistsException;
import pl.krzywyyy.barter.utils.exceptions.IncorrectEmailException;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(RegistrationUser registrationUser) throws AlreadyExistsException, IncorrectEmailException {
        if (userRepository.existsByEmail(registrationUser.getEmail())) {
            throw new AlreadyExistsException(User.class, registrationUser.getEmail());
        } else if (!checkIfEmailIsValid(registrationUser.getEmail())) {
            throw new IncorrectEmailException(registrationUser.getEmail());
        } else {
            User user = new User();
            user.setEmail(registrationUser.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(registrationUser.getPassword()));
            user.setFirstName(registrationUser.getEmail().split("\\.")[0]);
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.emptyList());
    }

    private boolean checkIfEmailIsValid(String email) {
        return email.toLowerCase().matches("[a-z]+(.)[a-z]+[0-9]*(@student.wat.edu.pl)");
    }
}
