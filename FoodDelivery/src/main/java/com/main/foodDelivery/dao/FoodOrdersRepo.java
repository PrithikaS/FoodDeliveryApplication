package com.main.foodDelivery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.foodDelivery.model.FoodOrders;

public interface FoodOrdersRepo extends JpaRepository<FoodOrders, Integer>{

	public FoodOrders findByFoodAndRestaurantAndUserId(String foodId, String restaurantId, int userId);
		
	public List<FoodOrders> findByOrderId(int orderId);
}
