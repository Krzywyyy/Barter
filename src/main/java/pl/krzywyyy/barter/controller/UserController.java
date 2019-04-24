package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
