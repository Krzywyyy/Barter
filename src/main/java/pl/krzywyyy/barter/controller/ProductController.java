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
	
	@GetMapping
	public Iterable<ProductDTO> findProducts(){
		return productService.findProducts();
	}

	@GetMapping("/{productId}")
	public ProductDTO findProduct(@PathVariable int productId) throws ObjectNotExistsException {
		return productService.findProduct(productId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO save(@RequestBody ProductDTO productDTO){
		String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return productService.save(productDTO,login);
	}

	@DeleteMapping("/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int productId) throws ObjectNotExistsException {
		productService.delete(productId);
	}

	@PutMapping("/{productId}")
	public ProductDTO update(@PathVariable int productId, @RequestBody ProductDTO productDTO) throws ObjectNotExistsException {
		return productService.update(productId,productDTO);
	}
	
}
