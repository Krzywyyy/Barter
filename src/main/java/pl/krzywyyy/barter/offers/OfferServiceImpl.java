package pl.krzywyyy.barter.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.products.Product;
import pl.krzywyyy.barter.products.ProductRepository;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.users.UserRepository;
import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;
import pl.krzywyyy.barter.utils.exceptions.OfferAlreadyConsideredException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper, UserRepository userRepository, ProductRepository productRepository) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Iterable<OfferDTO> findAllByProduct(int productId) throws ObjectNotExistsException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotExistsException(Product.class, String.valueOf(productId)));
        return offerRepository.findAllByProduct(product).stream().map(offerMapper::offerToOfferDTO).collect(Collectors.toList());
    }

    public Iterable<OfferDTO> findAllUserOffers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email);
        return offerRepository.findAllByOfferer(user).stream().map(offerMapper::offerToOfferDTO).collect(Collectors.toList());
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
            if (accepted) {
                Date date = new Date();
                offer.setConfirmDate(date);
                offerRepository.save(offer);
                rejectAnotherOffers(offerId, offer.getProduct());
                makeProductInactive(offer.getProduct());
            } else {
                Date date = Date.from(Instant.EPOCH);
                offer.setConfirmDate(date);
                offerRepository.save(offer);
            }
        }
        return offerMapper.offerToOfferDTO(offer);
    }

    private void makeProductInactive(Product product) {
        product.setActive(false);
        productRepository.save(product);
    }

    private void rejectAnotherOffers(int offerId, Product product) {
        List<Offer> anotherOffers = offerRepository.findAllByProductAndConfirmDateIsNullAndIdNot(product, offerId);
        Date date = Date.from(Instant.EPOCH);
        anotherOffers.forEach(offer -> {
            offer.setConfirmDate(date);
            offerRepository.save(offer);
        });
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
