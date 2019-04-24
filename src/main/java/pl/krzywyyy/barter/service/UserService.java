package pl.krzywyyy.barter.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.User;

@Service
public interface UserService extends CrudRepository<User,Integer>
{
}
