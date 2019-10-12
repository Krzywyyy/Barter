package pl.krzywyyy.barter.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Firstname cannot be blank")
    public String firstName;

    @NotBlank(message = "Lastname cannot be blank")
    private String lastName;

    @NotBlank
    @Email(message = "Email address is not valid!")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    private String password;

    @OneToMany(mappedBy = "user")
    List<Product> products;

}
