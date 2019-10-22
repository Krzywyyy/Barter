package pl.krzywyyy.barter.users;

import lombok.Data;
import pl.krzywyyy.barter.products.Product;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Firstname cannot be blank")
    public String firstName;

    @Column(unique = true)
    @NotBlank
    @Email(message = "Email address is not valid!")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    private String password;

    @OneToMany(mappedBy = "user")
    List<Product> products;
}
