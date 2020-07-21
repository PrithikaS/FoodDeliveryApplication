package com.main.foodDelivery.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
public class FoodOrders {

	@Id
	private int orderId;
	
	private int userId;
	
	private String userName;
	
	private String userAddress;
	
	private String contactNo;
	
	@JsonProperty("CreatedDate")
	private Date createdDate;
	
	@JsonProperty("UpdatedDate")
	private Date updatedDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "foodId")
	private Food food;
		
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurantId")
	private Restaurant restaurant;
}
