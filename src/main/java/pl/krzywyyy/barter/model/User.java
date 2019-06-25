package pl.krzywyyy.barter.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	@Size(min = 2,message = "Login must be between 2 and 16 characters long!")
	private String login;
	
	@NotBlank
	@Size(min = 6, message = "Password must be at least 6 characters long!")
	private String password;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;

	@Email(message = "Email address is not valid!")
	private String email;

//	@OneToMany(mappedBy = "user")
//	List<Product> products;
	
}
