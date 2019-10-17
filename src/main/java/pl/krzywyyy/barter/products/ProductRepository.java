package pl.krzywyyy.barter.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.utils.enums.ProductCategories;
import pl.krzywyyy.barter.utils.enums.Specializations;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Iterable<Product> findByCategory(ProductCategories category);

    Iterable<Product> findBySpecialization(Specializations specialization);
}
