package pl.krzywyyy.barter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.mapper.BarterMapper;
import pl.krzywyyy.barter.model.Product;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.model.dto.ProductDTO;
import pl.krzywyyy.barter.repository.ProductRepository;
import pl.krzywyyy.barter.repository.UserRepository;

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

    public ProductDTO save(ProductDTO productDTO, String email) {
        User user = userRepository.findByEmail(email);
        Product product = barterMapper.map(productDTO, Product.class);
        product.setUser(user);
        return barterMapper.map(productRepository.save(product), ProductDTO.class);
    }

    public void delete(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    public ProductDTO update(int productId, ProductDTO newProduct) throws ObjectNotExistsException {
        Product product = getProduct(productId);

        if (newProduct.getTitle() != null && !product.getTitle().equals(newProduct.getTitle())) {
            product.setTitle(newProduct.getTitle());
        }
        if (newProduct.getDescription() != null && !product.getDescription().equals(newProduct.getDescription())) {
            product.setDescription(newProduct.getDescription());
        }
        if (newProduct.getCategory() != null && !product.getCategory().equals(newProduct.getCategory())) {
            product.setCategory(newProduct.getCategory());
        }
        if (newProduct.getSpecialization() != null && !product.getSpecialization().equals(newProduct.getSpecialization())) {
            product.setSpecialization(newProduct.getSpecialization());
        }

        return barterMapper.map(productRepository.save(product), ProductDTO.class);
    }

    private Product getProduct(int productId) throws ObjectNotExistsException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotExistsException(Product.class, String.valueOf(productId)));
    }
}
