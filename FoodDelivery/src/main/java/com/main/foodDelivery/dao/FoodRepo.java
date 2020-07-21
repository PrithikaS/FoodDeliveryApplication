package com.main.foodDelivery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.foodDelivery.model.Food;

public interface FoodRepo extends JpaRepository<Food, Integer> {

	@Query(value="SELECT f.food_name,f.price,r.restaurant_name,r.restaurant_rating FROM food f join Restaurant r on f.food_id = r.food_id where food_name =:foodName", nativeQuery=true)
	public List<Food> findByFoodName(@Param("foodName") String foodName);
	
}
