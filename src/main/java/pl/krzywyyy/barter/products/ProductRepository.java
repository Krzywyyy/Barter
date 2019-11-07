package pl.krzywyyy.barter.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.users.User;

import java.util.List;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByUser(User user);
}
