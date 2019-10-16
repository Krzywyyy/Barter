package pl.krzywyyy.barter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.exception.AlreadyExistsException;
import pl.krzywyyy.barter.exception.IncorrectEmailException;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.repository.UserRepository;

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

    public void save(User user) throws AlreadyExistsException, IncorrectEmailException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException(User.class, user.getEmail());
        } else if (!checkIfEmailIsValid(user.getEmail())) {
            throw new IncorrectEmailException(user.getEmail());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setFirstName(user.getEmail().split("\\.")[0]);
            user.setLastName(user.getEmail().split("\\.")[1]);
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
        return email.matches("[a-zA-Z]+(.)[a-zA-Z]+(@student.wat.edu.pl)");
    }
}
