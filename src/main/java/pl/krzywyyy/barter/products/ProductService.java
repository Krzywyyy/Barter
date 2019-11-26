package pl.krzywyyy.barter.products;

import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.interfaces.CrudInterface;

public interface ProductService extends CrudInterface<ProductDTO> {
    Iterable<ProductDTO> findAll(int page);

    ProductDTO update(int productId, ProductDTO newProduct) throws ObjectNotExistsException;

    Iterable<ProductDTO> findAllUserProducts();
}
