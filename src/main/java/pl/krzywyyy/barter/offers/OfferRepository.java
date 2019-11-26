package pl.krzywyyy.barter.offers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzywyyy.barter.users.User;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    Iterable<Offer> findAllByOfferer(User user);
}
