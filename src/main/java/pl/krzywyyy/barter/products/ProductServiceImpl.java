package pl.krzywyyy.barter.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.users.UserRepository;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final int pageSize = 10;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Iterable<ProductDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return productRepository.findAll(pageable).stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO find(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        return ProductMapper.INSTANCE.productToProductDTO(product);
    }

    public ProductDTO save(ProductDTO productDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email);
        Product product = ProductMapper.INSTANCE.productDTOToProduct(productDTO);
        product.setUser(user);
        return ProductMapper.INSTANCE.productToProductDTO(productRepository.save(product));
    }

    public void delete(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    public ProductDTO update(int productId, ProductDTO updatedProduct) throws ObjectNotExistsException {
        Product product = getProduct(productId);

        product.setTitle(updatedProduct.getTitle() != null ? updatedProduct.getTitle() : product.getTitle());

        if (updatedProduct.getTitle() != null) {
            product.setTitle(updatedProduct.getTitle());
        }
        if (updatedProduct.getDescription() != null) {
            product.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getCategory() != null) {
            product.setCategory(updatedProduct.getCategory());
        }
        if (updatedProduct.getSpecialization() != null) {
            product.setSpecialization(updatedProduct.getSpecialization());
        }
        return ProductMapper.INSTANCE.productToProductDTO(productRepository.save(product));
    }

    private Product getProduct(int productId) throws ObjectNotExistsException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotExistsException(Product.class, String.valueOf(productId)));
    }
}
