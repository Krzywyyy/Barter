package pl.krzywyyy.barter.service;

import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.model.dto.ProductDTO;

public interface ProductService {
    Iterable<ProductDTO> findAll();

    ProductDTO find(int productId) throws ObjectNotExistsException;

    ProductDTO save(ProductDTO productDTO);

    void delete(int productId) throws ObjectNotExistsException;

    ProductDTO update(int productId, ProductDTO newProduct) throws ObjectNotExistsException;
}
