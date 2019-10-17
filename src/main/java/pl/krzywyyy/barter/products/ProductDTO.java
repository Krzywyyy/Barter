package pl.krzywyyy.barter.products;

import lombok.Data;
import pl.krzywyyy.barter.utils.enums.ProductCategories;
import pl.krzywyyy.barter.utils.enums.Specializations;

@Data
public class ProductDTO {
    private int id;
    private String title;
    private String description;
    private ProductCategories category;
    private Specializations specialization;
    private int userId;
}
