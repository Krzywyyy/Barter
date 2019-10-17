package pl.krzywyyy.barter.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.mapper.BarterMapper;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.users.UserRepository;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BarterMapper barterMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, BarterMapper barterMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.barterMapper = barterMapper;
    }

    public Iterable<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(e -> barterMapper.<ProductDTO>map(e, ProductDTO.class)).collect(Collectors.toList());
    }

    public ProductDTO find(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        return barterMapper.map(product, ProductDTO.class);
    }

    public ProductDTO save(ProductDTO productDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email);
        Product product = barterMapper.map(productDTO, Product.class);
        product.setUser(user);
        return barterMapper.map(productRepository.save(product), ProductDTO.class);
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
        return barterMapper.map(productRepository.save(product), ProductDTO.class);
    }

    private Product getProduct(int productId) throws ObjectNotExistsException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotExistsException(Product.class, String.valueOf(productId)));
    }
}
