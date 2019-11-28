package pl.krzywyyy.barter.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.utils.enums.ProductCategory;
import pl.krzywyyy.barter.utils.enums.Specialization;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productService = productServiceImpl;
    }

    @GetMapping(params = {"page"})
    public Iterable<ProductDTO> findProducts(
            @RequestParam(value = "category", defaultValue = "", required = false) String category,
            @RequestParam(value = "specialization", defaultValue = "", required = false) String specialization,
            @RequestParam(value = "page", defaultValue = "", required = false) int page) {
        ProductSearchFilters filters = new ProductSearchFilters(category, specialization);
        return productService.findAll(filters, page);
    }

    @GetMapping("/my")
    public Iterable<ProductDTO> findUserProducts() {
        return productService.findAllUserProducts();
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
