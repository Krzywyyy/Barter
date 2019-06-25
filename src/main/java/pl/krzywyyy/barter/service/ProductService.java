package pl.krzywyyy.barter.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.Product;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.model.dto.ProductDTO;
import pl.krzywyyy.barter.repository.ProductRepository;
import pl.krzywyyy.barter.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class ProductService
{
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public ProductService(ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper)
	{
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	public Iterable<ProductDTO> findProducts(){
		return productRepository.findAll().stream().map(ProductService.this::convertToDTO).collect(Collectors.toList());
	}
	
	public ProductDTO save(Product product, int userId){
		User user = userRepository.getOne(userId);
		product.setUser(user);
		productRepository.save(product);
		return convertToDTO(product);
	}
	
	private ProductDTO convertToDTO(Product product){
		return modelMapper.map(product,ProductDTO.class);
	}
	
	private Product convertToEntity(ProductDTO productDTO){
		return modelMapper.map(productDTO,Product.class);
	}
}
