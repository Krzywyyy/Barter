package pl.krzywyyy.barter.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	@NotBlank
	private String title;
	
	@Nullable
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
}
