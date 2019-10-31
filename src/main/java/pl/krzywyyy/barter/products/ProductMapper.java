package pl.krzywyyy.barter.products;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.krzywyyy.barter.users.UserRepository;

@Mapper(componentModel = "spring", uses = UserRepository.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    @Mapping(source = "user.id", target = "userId")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "userId", target = "user")
    Product productDTOToProduct(ProductDTO productDTO);
}
