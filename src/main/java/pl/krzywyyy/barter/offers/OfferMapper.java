package pl.krzywyyy.barter.offers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.krzywyyy.barter.products.ProductRepository;
import pl.krzywyyy.barter.users.UserRepository;

@Mapper(componentModel = "spring", uses = {ProductRepository.class, UserRepository.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OfferMapper {
    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "offerer.id", target = "offererId")
    })
    OfferDTO offerToOfferDTO(Offer offer);

    @Mappings({
            @Mapping(source = "productId", target = "product"),
            @Mapping(source = "offererId", target = "offerer")
    })
    Offer offerDTOToOffer(OfferDTO productDTO);
}
