package com.main.foodDelivery.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Restaurant {

	@Id
	private int restaurantId;
	
	private String restaurantName;
	
	private String restaurantRating;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "foodId")
	private Food food;
}
