package pl.krzywyyy.barter.products;

import lombok.Data;
import org.springframework.lang.Nullable;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.utils.enums.ProductCategories;
import pl.krzywyyy.barter.utils.enums.Specializations;

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
    private ProductCategories category;

    @NotNull
    private Specializations specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
