package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.service.UserService;

@RestController
public class UserController
{
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user){
		return userService.save(user);
	}
	
	@GetMapping("/users")
	public Iterable<User> findAllUsers(){
		return userService.findAll();
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User user){
		if(!userService.findById(user.getUserId()).isPresent()){
			return userService.save(user);
		}
		else throw new IllegalArgumentException("User does not exist!");
	}
	
	@DeleteMapping("/users")
	public void deleteUser(@RequestBody User user){
		if(userService.findById(user.getUserId()).isPresent()){
			userService.delete(user);
		}
		else throw new IllegalArgumentException("User does not exist");
	}
}
