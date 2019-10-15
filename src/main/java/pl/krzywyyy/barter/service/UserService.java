package pl.krzywyyy.barter.service;

import pl.krzywyyy.barter.exception.AlreadyExistsException;
import pl.krzywyyy.barter.exception.IncorrectEmailException;
import pl.krzywyyy.barter.model.User;

public interface UserService {
    void save(User user) throws AlreadyExistsException, IncorrectEmailException;
}
