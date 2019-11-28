package pl.krzywyyy.barter.products;

import lombok.Data;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.utils.enums.ProductCategory;
import pl.krzywyyy.barter.utils.enums.Specialization;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String image;

    @NotNull
    private ProductCategory category;

    @NotNull
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
