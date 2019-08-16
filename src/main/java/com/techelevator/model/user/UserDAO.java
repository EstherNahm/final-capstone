package com.techelevator.model.user;

import java.util.List;

import com.techelevator.model.restaurant.Restaurant;

public interface UserDAO {

	/*
	 * Adds a user to the database. Can only add a username and password,
	 * so use the appropriate methods to update other pieces of data, such as zip code
	 */
	public void saveUser(String userName, String password, String email);

	/*
	 * Checks if the password is correct for the given user
	 */
	public boolean isPasswordCorrect(String userName, String password);

	/*
	 * Changes the specified user's password
	 */
	public void updatePassword(String userName, String password);

	/*
	 * Returns the user with the specified username
	 */
	public User getUserByUsername(String userName);
	
	/*
	 * Returns the user with the specified ID
	 */
	public User getUserById(int id);
	
	/*
	 * Delete a user forever. Only use sparingly
	 */
	public void deleteUser(String username);
	
	/*
	 * Adds a food preference to the SQL table, but NOT to the POJO. After using this,
	 * you should probably run getUserFoodPreferences to update the POJO
	 */
	public void addFoodPreference(int userId, String foodType);
	
	/*
	 * Removes a food preference from the SQL table, but NOT from the POJO. After using this,
	 * you should probably run getUserFoodPreferences to update the POJO
	 */
	public void removeFoodPreference(int userId, String foodType);
	
	/*
	 * Returns a list of strings containing foods that the user likes
	 */
	public List<String> getUserFoodPreferences(int userId);
	
	/*
	 * Changes or adds the specified user's zip code
	 */
	public void updateZipcode(String username, String zipcode);
	
	/*
	 * Be careful to only put 1, 2, 3, or 4 as the budget, as this method doesn't
	 * verify that you did this!
	 * Updates the budget of the user with username username to budget
	 */
	public void updateBudget(String username, int budget);
	
	/*
	 * Marks the restaurant as a user favorite in the database
	 */
	public void addFavoriteRestaurant(int userId, String restaurantId);
	
	/*
	 * Unmarks the restaurant as a user favorite in the database
	 */
	public void unfavoriteRestaurant(int userId, String restaurantId);
	
	/*
	 * Returns a List<String> of restaurant ids that match the specified user's
	 * favorite restaurants
	 */
	public List<String> getFavoriteRestaurants(int userId);
	
	/*
	 * Returns true if the specified user has favorited the specified restaurant, returns false otherwise
	 */
	public boolean isRestaurantFavorite(int userId, String restaurantId);
	
	/*
	 * Changes or adds the specified user's e-mail
	 */
	public void updateEmail(String username, String email);

}
