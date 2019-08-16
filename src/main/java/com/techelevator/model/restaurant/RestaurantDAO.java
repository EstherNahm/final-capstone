package com.techelevator.model.restaurant;

import java.net.MalformedURLException;
import java.util.List;

import com.techelevator.model.user.User;

public interface RestaurantDAO {
	
	/*
	 * Returns a List of the hours the specified restaurant is open
	 */
	public List<DailyAvailability> getHoursOpen(String restaurantId);
	/*
	 * Returns the restaurant with the specified ID
	 */
	public Restaurant getRestaurantById(String id);

	/*
	 * Factoring the user's zip code, budget range, and food preferences in, gets a random
	 * restaurant that meets those criteria
	 */
	public Restaurant getRandomishRestaurant(User user, List<String> dislikedRestaurantIds);
	
	/*
	 * Gets a list of all yelp categories
	 */
	public List<Category> getAllYelpCategories();
	
	/*
	 * Given a string, tries to find a yelp category that has that preference. If it finds one,
	 * it returns the alias of that category. Otherwise, it returns failure
	 */
	public String getMatchingAlias(String foodType);
	

}
