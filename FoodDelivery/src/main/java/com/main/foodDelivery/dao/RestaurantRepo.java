package com.main.foodDelivery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.foodDelivery.model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

	
	@Query(value="select r.restaurant_id,r.restaurant_name,r.restaurant_rating,f.food_id,f.food_name,f.price from restaurant r join food f on r.food_id=f.food_id where r.restaurant_name = :restaurantName", nativeQuery=true)
	public List<Restaurant> findByRestaurantName(@Param("restaurantName") String restaurantName);
	
}
