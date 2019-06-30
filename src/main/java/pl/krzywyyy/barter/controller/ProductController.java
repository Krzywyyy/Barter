package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.model.dto.ProductDTO;
import pl.krzywyyy.barter.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController
{
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService)
	{
		this.productService = productService;
	}
	
	@GetMapping()
	public Iterable<ProductDTO> findProducts(){
		return productService.findProducts();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO save(@RequestBody ProductDTO productDTO){
		String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return productService.save(productDTO,login);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestBody ProductDTO productDTO) throws ObjectNotExistsException {
		productService.delete(productDTO);
	}
	
}
