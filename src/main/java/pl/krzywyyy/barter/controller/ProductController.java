package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
//	@PostMapping()
//	public ProductDTO save(ProductDTO productDTO){
//		productService.save(productDTO,1);
//	}
	
}
