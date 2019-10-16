package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.model.dto.ProductDTO;
import pl.krzywyyy.barter.service.ProductService;
import pl.krzywyyy.barter.service.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productService = productServiceImpl;
    }

    @GetMapping
    public Iterable<ProductDTO> findProducts() {
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    public ProductDTO findProduct(@PathVariable int productId) throws ObjectNotExistsException {
        return productService.find(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        return productService.save(productDTO);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int productId) throws ObjectNotExistsException {
        productService.delete(productId);
    }

    @PutMapping("/{productId}")
    public ProductDTO update(@PathVariable int productId, @RequestBody ProductDTO productDTO) throws ObjectNotExistsException {
        return productService.update(productId, productDTO);
    }
}
