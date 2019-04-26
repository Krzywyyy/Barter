package pl.krzywyyy.barter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.Product;

@Service
public interface ProductRepository extends JpaRepository<Product,Integer>
{

}
