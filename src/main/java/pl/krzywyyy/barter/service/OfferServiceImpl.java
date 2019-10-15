package pl.krzywyyy.barter.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.exception.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.model.Offer;
import pl.krzywyyy.barter.model.dto.OfferDTO;
import pl.krzywyyy.barter.repository.OfferRepository;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService{

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<OfferDTO> findAll() {
        return offerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OfferDTO find(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        return convertToDTO(offer);
    }

    public void delete(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        offerRepository.delete(offer);
    }

    public OfferDTO save(OfferDTO offerDTO) {
        offerDTO.setOfferDate(new Date());
        offerDTO.setConfirmDate(null);
        offerRepository.save(convertToEntity(offerDTO));
        return offerDTO;
    }

    public OfferDTO consider(int offerId, boolean accepted) throws ObjectNotExistsException, OfferAlreadyConsideredException {
        Offer offer = getOffer(offerId);

        if (offer.getConfirmDate() != null) throw new OfferAlreadyConsideredException(offerId);
        else {
            Date date = accepted ? new Date() : Date.from(Instant.EPOCH);
            offer.setConfirmDate(date);
            offerRepository.save(offer);
        }

        return convertToDTO(offer);
    }

    private Offer getOffer(int offerId) throws ObjectNotExistsException {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ObjectNotExistsException(Offer.class, String.valueOf(offerId)));
    }

    private Offer convertToEntity(OfferDTO offerDTO) {
        return modelMapper.map(offerDTO, Offer.class);
    }

    private OfferDTO convertToDTO(Offer offer) {
        return modelMapper.map(offer, OfferDTO.class);
    }
}
