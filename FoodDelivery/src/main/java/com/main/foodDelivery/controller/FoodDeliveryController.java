package com.main.foodDelivery.controller;

import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.foodDelivery.dao.FoodOrdersRepo;
import com.main.foodDelivery.dao.FoodRepo;
import com.main.foodDelivery.dao.RestaurantRepo;
import com.main.foodDelivery.model.Food;
import com.main.foodDelivery.model.FoodOrders;
import com.main.foodDelivery.model.Restaurant;

@RestController
public class FoodDeliveryController {

	@Autowired
	FoodOrdersRepo orderRepo;
	
	@Autowired
	FoodRepo foodRepo;
	
	@Autowired
	RestaurantRepo restaurantRepo;
	
	private final Logger log = LoggerFactory.getLogger(FoodDeliveryController.class);
	
	/*Scenario :
	 * User Browse catalogue By Food */	
	
	@RequestMapping("/searchByFood")
	public List<Food> searchByFood(@RequestParam String foodName) {
		
		List<Food> foodDetails =  foodRepo.findByFoodName(foodName);
		if(foodDetails.isEmpty()) {
			log.info("No FoodItem found for the name");
		}
		return foodDetails;
	}
	
	/*Scenario :
	 * User Browse catalogue By Restaurant to get the food available */	
	
	@RequestMapping("/searchByRestaurant")
	public List<Restaurant> searchByRestaurant(@RequestParam String restaurantName) {
		
		List<Restaurant> restaurantDetails = restaurantRepo.findByRestaurantName(restaurantName);
		if(restaurantDetails.isEmpty()) {
			log.info("No Restaurant available in the given search name ");
		}
		return restaurantDetails;
	}
	
	/*Scenario :
	 * User Add's or updated the food order
	 * The userId's and Food Id's will be available from the UI during intial loading */	
	
	@PutMapping(value = "/addOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public FoodOrders addOrder(@RequestBody FoodOrders foodOrder) {
		try {
			FoodOrders foodOrders =orderRepo.findByFoodAndRestaurantAndUserId(foodOrder.getFood().toString(), 
					foodOrder.getRestaurant().toString(), foodOrder.getUserId());
			
			if(Objects.isNull(foodOrders)) {
				log.info("Add new order");
				orderRepo.save(foodOrder);
				System.out.println("New order added");
			}else {
				log.info("Update the existing order");
				foodOrders.setFood(foodOrder.getFood());
				foodOrders.setRestaurant(foodOrder.getRestaurant());
				foodOrders.setUserAddress(foodOrder.getUserAddress());
			}
		}catch(Exception e){
			log.error("Adding food order failed", e);
		}
		return foodOrder;
	}
	
	/*Scenario :
	 * User Add's or updated the food order
	 * The deletes the food order */
	
	@DeleteMapping(value = "/deleteOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOrder(@RequestBody FoodOrders foodOrder) {
		try {
			FoodOrders foodOrders = orderRepo.getOne(foodOrder.getOrderId());
			orderRepo.delete(foodOrders);
		}catch(Exception e) {
			log.error("Deleting food order failed", e);
		}		
	}
	
	/*Scenario :
	 * Summarize data of bookings with prize */
	
	@PostMapping(value ="/orderSummary", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String orderSummary(@RequestParam int userID){
		JSONObject orderSummaryDetails = new JSONObject();
		JSONArray foodOrderDetails =new JSONArray();
		Integer totalPrize =0;
		try {
			List<FoodOrders> orderSummary= orderRepo.findByOrderId(userID);
			if(!orderSummary.isEmpty()) {
				for(FoodOrders food : orderSummary) {
					foodOrderDetails.put(food);
					Food f =food.getFood();
					totalPrize=totalPrize + Integer.parseInt(f.getPrice());
				}
				orderSummaryDetails.put("FoodOrderDetails", foodOrderDetails);
				orderSummaryDetails.put("TotalPrize", totalPrize);
			}			
		}catch(JSONException e) {
			log.info("Order details not available properly");
		}
		catch(Exception e) {
			log.info("Unable to fetch Order Summary");
		}
		return orderSummaryDetails.toString();
	}
}
