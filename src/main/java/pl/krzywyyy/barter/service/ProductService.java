package pl.krzywyyy.barter.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.Product;

@Service
public interface ProductService extends CrudRepository<Product,Integer>
{
}
