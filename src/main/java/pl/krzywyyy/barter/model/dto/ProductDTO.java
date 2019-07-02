package pl.krzywyyy.barter.model.dto;

import lombok.Data;
import pl.krzywyyy.barter.model.enums.ProductCategories;
import pl.krzywyyy.barter.model.enums.Specializations;

@Data
public class ProductDTO
{
	private int id;
	private String title;
	private String description;
	private ProductCategories category;
	private Specializations specialization;
	private int userId;
}
