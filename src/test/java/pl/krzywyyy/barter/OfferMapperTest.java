package pl.krzywyyy.barter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.krzywyyy.barter.offers.Offer;
import pl.krzywyyy.barter.offers.OfferDTO;
import pl.krzywyyy.barter.offers.OfferMapper;
import pl.krzywyyy.barter.products.Product;
import pl.krzywyyy.barter.users.User;

import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OfferMapperTest {

    @Autowired
    private OfferMapper offerMapper;

    @Test
    public void shouldMapOfferToOfferDTO() {
        Date date = new Date();

        User user = new User();
        user.setId(1);

        Product product = new Product();
        product.setId(1);

        Offer offer = new Offer();
        offer.setId(1);
        offer.setMessage("message");
        offer.setOfferDate(date);
        offer.setOfferer(user);
        offer.setProduct(product);

        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        assertNotNull(offerDTO);
        assertEquals(offerDTO.getId(), offer.getId());
        assertEquals(offerDTO.getMessage(), "message");
        assertEquals(offerDTO.getProductId(), offer.getProduct().getId());
        assertEquals(offerDTO.getOffererId(), offer.getOfferer().getId());
        assertEquals(offerDTO.getOfferDate(), offer.getOfferDate());
        assertNull(offerDTO.getConfirmDate());
    }
}
