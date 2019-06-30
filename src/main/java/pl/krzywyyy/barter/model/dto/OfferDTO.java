package pl.krzywyyy.barter.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OfferDTO
{
	private int offeredProductId;
	private int aimedProductId;
	private Date offerDate;
	private Date confirmDate;
}
