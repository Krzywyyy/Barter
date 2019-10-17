package pl.krzywyyy.barter.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.utils.mapper.BarterMapper;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final BarterMapper barterMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, BarterMapper barterMapper) {
        this.offerRepository = offerRepository;
        this.barterMapper = barterMapper;
    }

    public Iterable<OfferDTO> findAll() {
        return offerRepository.findAll().stream().map(e -> barterMapper.<OfferDTO>map(e, OfferDTO.class)).collect(Collectors.toList());
    }

    public OfferDTO find(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        return barterMapper.map(offer, OfferDTO.class);
    }

    public void delete(int offerId) throws ObjectNotExistsException {
        Offer offer = getOffer(offerId);
        offerRepository.delete(offer);
    }

    public OfferDTO save(OfferDTO offerDTO) {
        offerDTO.setOfferDate(new Date());
        offerDTO.setConfirmDate(null);
        offerRepository.save(barterMapper.map(offerDTO, Offer.class));
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

        return barterMapper.map(offer, OfferDTO.class);
    }

    private Offer getOffer(int offerId) throws ObjectNotExistsException {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ObjectNotExistsException(Offer.class, String.valueOf(offerId)));
    }
}
