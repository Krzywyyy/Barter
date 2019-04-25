package pl.krzywyyy.barter.controller;

import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.model.Product;
import pl.krzywyyy.barter.model.User;
import pl.krzywyyy.barter.service.ProductService;
import pl.krzywyyy.barter.service.UserService;

@RestController
public class ProductController
{
	private final ProductService productService;
	private final UserService userService;
	
	public ProductController(ProductService productService, UserService userService)
	{
		this.productService = productService;
		this.userService = userService;
	}

	@PostMapping("/products/{id}")
	public Product addProduct(@PathVariable int id, @RequestBody Product product){

		User user = userService.findById(id).get();
		product.setUser(user);
		return productService.save(product);
	}
	
	@GetMapping("/products")
	public Iterable<Product> findAllProducts(){
		return productService.findAll();
	}
	
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product){
		if(productService.findById(product.getProductId()).isPresent()){
			return productService.save(product);
		}
		else throw new IllegalArgumentException("Product does not exist!");
	}
	
	@DeleteMapping("/products")
	public void deleteProduct(@RequestBody Product product){
		if(productService.findById(product.getProductId()).isPresent()){
			productService.delete(product);
		}
		else throw new IllegalArgumentException("Product does not exist");
	}
}
