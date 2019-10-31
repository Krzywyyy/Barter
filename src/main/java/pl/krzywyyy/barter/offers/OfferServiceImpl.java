package pl.krzywyyy.barter.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final int pageSize = 10;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    public Iterable<OfferDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return offerRepository.findAll(pageable).stream().map(offerMapper::offerToOfferDTO).collect(Collectors.toList());
    }

    public OfferDTO find(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        return offerMapper.offerToOfferDTO(offer);
    }

    public void delete(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        offerRepository.delete(offer);
    }

    public OfferDTO save(OfferDTO offerDTO) {
        offerDTO.setOfferDate(new Date());
        offerDTO.setConfirmDate(null);
        offerRepository.save(offerMapper.offerDTOToOffer(offerDTO));
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

        return offerMapper.offerToOfferDTO(offer);
    }

    private Offer getOffer(int offerId) throws ObjectNotExistsException {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ObjectNotExistsException(Offer.class, String.valueOf(offerId)));
    }
}
