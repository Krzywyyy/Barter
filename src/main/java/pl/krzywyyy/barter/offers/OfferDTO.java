package pl.krzywyyy.barter.offers;

import lombok.Data;

import java.util.Date;

@Data
public class OfferDTO {
    private int id;
    private int offeredProductId;
    private int aimedProductId;
    private Date offerDate;
    private Date confirmDate;
}