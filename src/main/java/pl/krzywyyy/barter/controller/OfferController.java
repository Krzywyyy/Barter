package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.model.Offer;
import pl.krzywyyy.barter.repository.OfferRepository;
import pl.krzywyyy.barter.repository.ProductRepository;

import java.util.Date;

@RestController
public class OfferController
{
	private final OfferRepository offerRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public OfferController(OfferRepository offerRepository, ProductRepository productRepository)
	{
		this.offerRepository = offerRepository;
		this.productRepository = productRepository;
	}
	
	@GetMapping("/offers")
	public Iterable<Offer> findAllOffers(){
		return offerRepository.findAll();
	}
	
	@PostMapping("/offers/{offeredId},{aimedId}")
	public Offer addOffer(@PathVariable int offeredId, @PathVariable int aimedId, Offer offer){
		
		offer.setOfferedProduct(productRepository.findById(offeredId).get());
		offer.setAimedProduct(productRepository.findById(aimedId).get());
		offer.setOfferDate(new Date());
		return offerRepository.save(offer);
	}
	
	@PutMapping("/offers")
	public Offer updateOffer(@RequestBody Offer offer){
		if(offerRepository.findById(offer.getOfferId()).isPresent()){
			return offerRepository.save(offer);
		}
		else throw new IllegalArgumentException("Offer does not exist!");
	}
	
	@DeleteMapping("/offers")
	public void deleteOffer(@RequestBody Offer offer){
		if(offerRepository.findById(offer.getOfferId()).isPresent()){
			offerRepository.delete(offer);
		}
		else throw new IllegalArgumentException("Offer does not exist!");
	}
	
}
