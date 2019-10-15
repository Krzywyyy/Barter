package pl.krzywyyy.barter.service;

import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.exception.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.model.dto.OfferDTO;

public interface OfferService {
    Iterable<OfferDTO> findAll();

    OfferDTO find(int offerId) throws ObjectNotExistsException;

    void delete(int offerId) throws ObjectNotExistsException;

    OfferDTO save(OfferDTO offerDTO);

    OfferDTO consider(int offerId, boolean accepted) throws ObjectNotExistsException, OfferAlreadyConsideredException;
}
