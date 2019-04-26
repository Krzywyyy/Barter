package pl.krzywyyy.barter.controller;

import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.model.Product;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.repository.ProductRepository;
import pl.krzywyyy.barter.repository.UserRepository;

@RestController
public class ProductController
{
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	public ProductController(ProductRepository productRepository, UserRepository userRepository)
	{
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@PostMapping("/products/{id}")
	public Product addProduct(@PathVariable int id, @RequestBody Product product){

		User user = userRepository.findById(id).get();
		product.setUser(user);
		return productRepository.save(product);
	}
	
	@GetMapping("/products")
	public Iterable<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product){
		if(productRepository.findById(product.getProductId()).isPresent()){
			return productRepository.save(product);
		}
		else throw new IllegalArgumentException("Product does not exist!");
	}
	
	@DeleteMapping("/products")
	public void deleteProduct(@RequestBody Product product){
		if(productRepository.findById(product.getProductId()).isPresent()){
			productRepository.delete(product);
		}
		else throw new IllegalArgumentException("Product does not exist");
	}
}
