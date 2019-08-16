package com.techelevator.visit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Visit {
	private int visitId;
	private int userId;
	private String restaurantId;
	private String review;
	private int rating;
	private LocalDate dateOfVisit;
	private String username;
	private boolean isUsersReview;
	
	public boolean getIsUsersReview() {
		return isUsersReview;
	}
	public void setIsUsersReview(int userId) {
		if (this.userId == userId) {
			isUsersReview = true;
		} else {
			isUsersReview = false;
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getVisitId() {
		return visitId;
	}
	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getDateOfVisit() {
		String formattedDate = dateOfVisit.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		return formattedDate;
	}
	public void setDateOfVisit(LocalDate dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
}
