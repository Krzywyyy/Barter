package pl.krzywyyy.barter.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.users.UserRepository;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;
import pl.krzywyyy.barter.utils.properties.PageProperties;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserRepository userRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.userRepository = userRepository;
    }

    public Iterable<OfferDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, PageProperties.PAGE_SIZE);
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
        offerDTO.setOffererId(getUserId());
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

    private int getUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email);
        return user.getId();
    }

    private Offer getOffer(int offerId) throws ObjectNotExistsException {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ObjectNotExistsException(Offer.class, String.valueOf(offerId)));
    }
}
