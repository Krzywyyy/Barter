package pl.krzywyyy.barter.offers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.krzywyyy.barter.products.ProductRepository;

@Mapper(componentModel = "spring", uses = ProductRepository.class)
public interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

    @Mappings({
            @Mapping(source = "offeredProduct.id", target = "offeredProductId"),
            @Mapping(source = "aimedProduct.id", target = "aimedProductId")
    })
    OfferDTO offerToOfferDTO(Offer offer);

    @Mappings({
            @Mapping(source = "offeredProductId", target = "offeredProduct"),
            @Mapping(source = "aimedProductId", target = "aimedProduct")
    })
    Offer offerDTOToOffer(OfferDTO productDTO);
}
