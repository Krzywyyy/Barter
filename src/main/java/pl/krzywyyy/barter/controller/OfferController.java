package pl.krzywyyy.barter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.exception.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.model.dto.OfferDTO;
import pl.krzywyyy.barter.service.OfferService;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public Iterable<OfferDTO> findOffers() {
        return offerService.findOffers();
    }

    @GetMapping("/{offerId}")
    public OfferDTO findOffer(@PathVariable int offerId) throws ObjectNotExistsException {
        return offerService.findOffer(offerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDTO save(@RequestBody OfferDTO offerDTO) {
        return offerService.save(offerDTO);
    }

    @DeleteMapping("/{offerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int offerId) throws ObjectNotExistsException {
        offerService.delete(offerId);
    }

    @PutMapping("/{offerId}")
    public OfferDTO consider(@PathVariable int offerId, @RequestParam(name = "accepted") boolean accepted)
            throws OfferAlreadyConsideredException, ObjectNotExistsException {
        return offerService.consider(offerId, accepted);
    }

}
