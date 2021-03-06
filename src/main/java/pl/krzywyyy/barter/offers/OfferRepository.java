package pl.krzywyyy.barter.offers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzywyyy.barter.products.Product;
import pl.krzywyyy.barter.users.User;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findAllByProduct(Product product);

    List<Offer> findAllByOfferer(User user);

    List<Offer> findAllByProductAndConfirmDateIsNullAndIdNot(Product product, int offerId);
}
