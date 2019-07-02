package pl.krzywyyy.barter.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.model.Product;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.model.dto.ProductDTO;
import pl.krzywyyy.barter.repository.ProductRepository;
import pl.krzywyyy.barter.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<ProductDTO> findProducts() {
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProductDTO findProduct(int productId) throws ObjectNotExistsException {
        Product product = getProduct(productId);
        return convertToDTO(product);
    }


    public ProductDTO save(ProductDTO productDTO, String login) {
        User user = userRepository.findByLogin(login);
        Product product = convertToEntity(productDTO);
        product.setUser(user);
        return convertToDTO(productRepository.save(product));
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

        return convertToDTO(productRepository.save(product));
    }

    private Product getProduct(int productId) throws ObjectNotExistsException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotExistsException(Product.class, String.valueOf(productId)));
    }

    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }


}
