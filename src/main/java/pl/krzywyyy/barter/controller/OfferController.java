package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.model.Offer;
import pl.krzywyyy.barter.service.OfferService;
import pl.krzywyyy.barter.service.ProductService;

import java.util.Date;

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
		offer.setOfferDate(new Date());
		return offerService.save(offer);
	}
	
	@PutMapping("/offers")
	public Offer updateOffer(@RequestBody Offer offer){
		if(offerService.findById(offer.getOfferId()).isPresent()){
			return offerService.save(offer);
		}
		else throw new IllegalArgumentException("Offer does not exist!");
	}
	
	@DeleteMapping("/offers")
	public void deleteOffer(@RequestBody Offer offer){
		if(offerService.findById(offer.getOfferId()).isPresent()){
			offerService.delete(offer);
		}
		else throw new IllegalArgumentException("Offer does not exist!");
	}
	
}
