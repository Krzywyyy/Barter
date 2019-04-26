package pl.krzywyyy.barter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzywyyy.barter.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer>
{
}