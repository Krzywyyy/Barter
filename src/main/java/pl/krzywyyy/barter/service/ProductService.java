package pl.krzywyyy.barter.service;

import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.model.dto.ProductDTO;

public interface ProductService extends CrudInterface<ProductDTO> {
    ProductDTO update(int productId, ProductDTO newProduct) throws ObjectNotExistsException;
}
