package pl.krzywyyy.barter.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.exception.ObjectNotExistsException;
import pl.krzywyyy.barter.model.Offer;
import pl.krzywyyy.barter.model.dto.OfferDTO;
import pl.krzywyyy.barter.repository.OfferRepository;
import pl.krzywyyy.barter.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<OfferDTO> findOffers() {
        return offerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OfferDTO findOffer(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        return convertToDTO(offer);
    }

    public void delete(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        offerRepository.delete(offer);
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
