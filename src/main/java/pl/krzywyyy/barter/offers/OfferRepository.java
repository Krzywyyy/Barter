package pl.krzywyyy.barter.offers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzywyyy.barter.offers.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
