package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.repository.UserRepository;

@RestController
public class UserController
{
	private final UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user){
		return userRepository.save(user);
	}
	
	@GetMapping("/users")
	public Iterable<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User user){
		if(!userRepository.findById(user.getUserId()).isPresent()){
			return userRepository.save(user);
		}
		else throw new IllegalArgumentException("User does not exist!");
	}
	
	@DeleteMapping("/users")
	public void deleteUser(@RequestBody User user){
		if(userRepository.findById(user.getUserId()).isPresent()){
			userRepository.delete(user);
		}
		else throw new IllegalArgumentException("User does not exist");
	}
}
