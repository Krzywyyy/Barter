package pl.krzywyyy.barter.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.users.UserRepository;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.files.ImageFileReader;
import pl.krzywyyy.barter.utils.files.ImageFileWriter;
import pl.krzywyyy.barter.utils.properties.ProductImagesProperties;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
    }

    public Iterable<ProductDTO> findAll(ProductSearchFilters filters, int page, int productsPerPage) {
        Specification<Product> specification = ProductSpecification.getSpecification(filters);
        Pageable pageable = PageRequest.of(page - 1, productsPerPage);
         Page<Product> products = productRepository.findAll(specification, pageable);
        for (Product product : products) encodeImage(product);
        return products.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }

    public Iterable<ProductDTO> findAllUserProducts() {
        User user = getUser();
        List<Product> userProducts = productRepository.findAllByUser(user);
        for (Product product : userProducts) encodeImage(product);
        return userProducts.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO find(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        encodeImage(product);
        return productMapper.productToProductDTO(product);
    }

    public ProductDTO save(ProductDTO productDTO) {
        User user = getUser();
        productDTO.setUserId(user.getId());

        productDTO.setImage(
                productDTO.getImage() != null ?
                        ImageFileWriter.decodeAndSave(productDTO.getImage()) : ProductImagesProperties.NO_IMAGE
        );
        Product product = productMapper.productDTOToProduct(productDTO);
        return productMapper.productToProductDTO(productRepository.save(product));
    }

    public void delete(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    public ProductDTO update(int productId, ProductDTO updatedProduct) throws ObjectNotExistsException {
        Product product = getProduct(productId);

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
        return productMapper.productToProductDTO(productRepository.save(product));
    }

    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userRepository.findByEmail(email);
    }

    private void encodeImage(Product product) {
        String encodedImage = ImageFileReader.readAndEncode(product.getImage());
        product.setImage(encodedImage);
    }

    private Product getProduct(int productId) throws ObjectNotExistsException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotExistsException(Product.class, String.valueOf(productId)));
    }
}
