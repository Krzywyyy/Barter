package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzywyyy.barter.model.Offer;
import pl.krzywyyy.barter.service.OfferService;
import pl.krzywyyy.barter.service.ProductService;

@RestController
public class OfferController
{
	private OfferService offerService;
	private ProductService productService;
	
	@Autowired
	public OfferController(OfferService offerService, ProductService productService)
	{
		this.offerService = offerService;
		this.productService = productService;
	}
	
	@GetMapping("/offers")
	public Iterable<Offer> findAllOffers(){
		return offerService.findAll();
	}
	
	@PostMapping("/offers/{offeredId},{aimedId}")
	public Offer addOffer(@PathVariable int offeredId, @PathVariable int aimedId, Offer offer){
		
		offer.setOfferedProduct(productService.findById(offeredId).get());
		offer.setAimedProduct(productService.findById(aimedId).get());
		return offerService.save(offer);
	}
	
}
