package pl.krzywyyy.barter.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferServiceImpl offerService) {
        this.offerService = offerService;
    }

    @GetMapping()
    public Iterable<OfferDTO> findAllByProduct(@RequestParam("productId") int productId) throws ObjectNotExistsException {
        return offerService.findAllByProduct(productId);
    }

    @GetMapping("/my")
    public Iterable<OfferDTO> findAllUserOffers() {
        return offerService.findAllUserOffers();
    }

    @GetMapping("/{offerId}")
    public OfferDTO findOffer(@PathVariable int offerId) throws ObjectNotExistsException {
        return offerService.find(offerId);
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
