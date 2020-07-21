package com.main.foodDelivery.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Food {

	@Id
	private int foodId;
	
	private String foodName;
	
	private String price;
	
} 
