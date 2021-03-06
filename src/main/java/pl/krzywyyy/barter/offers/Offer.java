package pl.krzywyyy.barter.offers;

import lombok.Data;
import org.springframework.lang.Nullable;
import pl.krzywyyy.barter.products.Product;
import pl.krzywyyy.barter.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offerer_id")
    private User offerer;

    private Date offerDate;

    @Nullable
    private Date confirmDate;
}
