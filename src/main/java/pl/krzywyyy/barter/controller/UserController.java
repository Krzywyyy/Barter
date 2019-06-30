package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzywyyy.barter.exception.AlreadyExistsException;
import pl.krzywyyy.barter.exception.IncorrectEmailException;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController
{
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public void signUp(@RequestBody User user) throws AlreadyExistsException, IncorrectEmailException {
		userService.save(user);
	}
}
