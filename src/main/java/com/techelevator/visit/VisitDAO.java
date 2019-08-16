package com.techelevator.visit;

import java.time.LocalDate;
import java.util.List;

public interface VisitDAO {
	
	/*
	 * Logs a visit for the specified user at the specified restaurant. Uses the current day
	 * instead of specifying a date
	 */
	public void saveVisit(int userId, String restaurantId);
	
	/*
	 * Logs a visit for the specified user at the specified restaurant. Inputs the
	 * specified date
	 */
	public void saveVisit(int userId, String restaurantId, LocalDate dateOfVisit);
	
	/*
	 * Updates or adds a review for the specified visit
	 */
	public void updateReview(int visitId, String text, int rating);
	
	/*
	 * Returns all visits the specified user made to the specified restaurant
	 */
	public List<Visit> searchForVisits(int userId, String restaurantId);
	
	/*
	 * Returns the visit with the specified ID
	 */
	public Visit getVisitById(int visitId);
	
	/*
	 * Returns all visits anyone has made at the specified restaurant
	 */
	public List<Visit> getVisitsForRestaurant(String restaurantId);
	
	/*
	 * Returns all visits the specified user has made to any restaurant
	 */
	public List<Visit> getVisitsForUser(int userId);
	
	/*
	 * Gets the average rating for all reviews at the specified restaurant
	 */
	public double getAverageRating(String restaurantId);
	
	/*
	 * Gets the most recent visit for the specified user
	 */
	public Visit getMostRecentVisit(int userId);
	
	/*
	 * Gets the most recent visit for the specified user at the specified restaurant
	 */
	public Visit getMostRecentVisit(int userId, String restaurantId);
	
	/*
	 * Returns true if the specified visit has a review, returns false otherwise
	 */
	public boolean visitHasReview(int visitId);
	
	/*
	 * Returns true if the user has been asked about a certain visit
	 */
	public boolean hasBeenAskedABout(int visitId);
	
	/*
	 * Sets has_been_asked_about to true
	 */
	public void askAbout(int visitId);
	
	/*
	 * Delete delete a review but not the visit itself
	 */
	public void deleteReview(int visitId);
	
	/*
	 * Delete the specified visit
	 */
	public void deleteVisit(int visitId);
	
}
