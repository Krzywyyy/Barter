package pl.krzywyyy.barter.offers;

import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.utils.interfaces.CrudInterface;

import java.util.List;

public interface OfferService extends CrudInterface<OfferDTO> {
    Iterable<OfferDTO> findAllByProduct(int productId) throws ObjectNotExistsException;
    Iterable<OfferDTO> findAllUserOffers();
    OfferDTO consider(int offerId, boolean accepted) throws ObjectNotExistsException, OfferAlreadyConsideredException;
}
