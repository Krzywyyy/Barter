package pl.krzywyyy.barter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.Product;
import pl.krzywyyy.barter.model.enums.ProductCategories;
import pl.krzywyyy.barter.model.enums.Specializations;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Iterable<Product> findByCategory(ProductCategories category);

    Iterable<Product> findBySpecialization(Specializations specialization);
}
