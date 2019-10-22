package pl.krzywyyy.barter.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.krzywyyy.barter.users.UserRepository;

@Mapper(componentModel = "spring", uses = UserRepository.class)
interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "userId", target = "user")
    Product productDTOToProduct(ProductDTO productDTO);
}
