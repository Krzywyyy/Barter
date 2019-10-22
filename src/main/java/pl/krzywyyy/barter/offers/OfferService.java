package pl.krzywyyy.barter.offers;

import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.utils.interfaces.CrudInterface;

public interface OfferService extends CrudInterface<OfferDTO> {
    OfferDTO consider(int offerId, boolean accepted) throws ObjectNotExistsException, OfferAlreadyConsideredException;
}
