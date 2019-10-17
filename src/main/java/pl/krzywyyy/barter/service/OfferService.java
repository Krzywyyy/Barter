package pl.krzywyyy.barter.service;

import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.exception.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.model.dto.OfferDTO;

public interface OfferService extends CrudInterface<OfferDTO> {
    OfferDTO consider(int offerId, boolean accepted) throws ObjectNotExistsException, OfferAlreadyConsideredException;
}
