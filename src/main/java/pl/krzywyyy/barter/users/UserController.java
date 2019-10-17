package pl.krzywyyy.barter.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzywyyy.barter.utils.exceptions.AlreadyExistsException;
import pl.krzywyyy.barter.utils.exceptions.IncorrectEmailException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void signUp(@RequestBody User user) throws AlreadyExistsException, IncorrectEmailException {
        userService.save(user);
    }
}
