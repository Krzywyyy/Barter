package pl.krzywyyy.barter.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.model.Offer;

@Service
public interface OfferService extends CrudRepository<Offer,Integer>
{
}
