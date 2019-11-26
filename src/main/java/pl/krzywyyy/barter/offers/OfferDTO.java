package pl.krzywyyy.barter.offers;

import lombok.Data;

import java.util.Date;

@Data
public class OfferDTO {
    private int id;
    private String title;
    private String message;
    private int productId;
    private int offererId;
    private Date offerDate;
    private Date confirmDate;
}
