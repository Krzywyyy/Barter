package pl.krzywyyy.barter.service;

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
public class UserService implements UserDetailsService
{
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void save(User user) throws AlreadyExistsException, IncorrectEmailException
	{
		if(userRepository.existsByEmail(user.getEmail()))
			throw new AlreadyExistsException(User.class,user.getEmail());
		else if(!checkIfEmailIsValid(user.getEmail())){
			throw new IncorrectEmailException(user.getEmail());
		}
		else{
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		}
	}
	
	private boolean checkIfEmailIsValid(String email)
	{
		return email.matches("[a-zA-Z]+(.)[a-zA-Z]+(@student.wat.edu.pl)");
	}
	
	@Override
	public UserDetails loadUserByUsername(String login){
		User user = userRepository.findByLogin(login);
		if(user == null) throw new UsernameNotFoundException(login);
		return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(),
				Collections.emptyList());
	}
}
