package pl.krzywyyy.barter.model;

import lombok.Data;
import pl.krzywyyy.barter.model.enums.ProductCategories;
import pl.krzywyyy.barter.model.enums.Specializations;

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

    @NotNull
    private ProductCategories category;

    @NotNull
    private Specializations specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
