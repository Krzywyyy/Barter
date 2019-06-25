package pl.krzywyyy.barter.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Offer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "offeredProductId")
	private Product offeredProduct;
	
	@OneToOne
	@JoinColumn(name = "aimedProductId")
	private Product aimedProduct;
	
	private Date offerDate;
	
	@Nullable
	private Date confirmDate;
}
