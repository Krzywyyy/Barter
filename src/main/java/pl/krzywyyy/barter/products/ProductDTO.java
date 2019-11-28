package pl.krzywyyy.barter.products;

import lombok.Data;
import pl.krzywyyy.barter.utils.enums.ProductCategory;
import pl.krzywyyy.barter.utils.enums.Specialization;

@Data
public class ProductDTO {
    private int id;
    private String title;
    private String description;
    private String image;
    private ProductCategory category;
    private Specialization specialization;
    private int userId;
}
