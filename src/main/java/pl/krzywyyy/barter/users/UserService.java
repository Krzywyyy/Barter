package pl.krzywyyy.barter.users;

import pl.krzywyyy.barter.utils.exceptions.AlreadyExistsException;
import pl.krzywyyy.barter.utils.exceptions.IncorrectEmailException;

public interface UserService {
    void save(User user) throws AlreadyExistsException, IncorrectEmailException;
}
