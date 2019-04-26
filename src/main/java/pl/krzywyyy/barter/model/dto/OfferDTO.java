package pl.krzywyyy.barter.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OfferDTO
{
	private long offeredProductId;
	private long aimedProductId;
	private Date offerDate;
	private Date confirmDate;
}
