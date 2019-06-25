package pl.krzywyyy.barter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.User;

@Service
public interface UserRepository extends JpaRepository<User,Integer>
{
	User findByLogin(String login);
	boolean existsByEmail(String email);
}
