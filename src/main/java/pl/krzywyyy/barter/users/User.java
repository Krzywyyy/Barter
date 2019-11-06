package pl.krzywyyy.barter.users;

import lombok.Data;
import pl.krzywyyy.barter.products.Product;
import pl.krzywyyy.barter.users.roles.Role;

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
    private String firstName;

    @Column(unique = true)
    @NotBlank
    @Email(message = "Email address is not valid!")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
}
