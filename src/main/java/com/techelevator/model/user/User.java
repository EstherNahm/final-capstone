package com.techelevator.model.user;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class User {

	private String username;
	private String password;
	private String role;
	private int id;
	private String email;
	private List<String> foodPreferences;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String confirmPassword;
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	private String zipcode;
	
	private int budget;
	
	public String getUserName() {
		return username;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getFoodPreferences() {
		return foodPreferences;
	}
	public void setFoodPreferences(List<String> foodPreferences) {
		this.foodPreferences = foodPreferences;
	}
	public void addFoodPreference(String foodType) {
		foodPreferences.add(foodType);
	}
	public String validateUsername() {
		if (username.equals("") || username == null) {
			return "Please enter a username";
		}
		if (username.contains(" ")) {
			return "Usernames cannot contain spaces";
		}
		return ""; //used to indicate that a valid input was returned
	}
	public String validatePassword() {
		if (password.length() < 10) {
			return "Passwords must be at least 10 characters";
		}
		if (!password.equals(confirmPassword)) {
			return "Passwords must match";
		}
//		String lowerCase = password.toLowerCase();
//		String upperCase = password.toUpperCase();
//		if (password.equals(lowerCase)) {
//			return "Passwords must include an upper case letter";
//		}
//		if (password.equals(upperCase)) {
//			return "Passwords must include a lower case letter";
//		}
		return ""; //used to indicate that a valid input was returned
	}
	public String validateBudget() {
		if (budget < 1) {
			return "Budget must be at least 1.";
		}
		if (budget > 4) {
			return "Budget cannot be bigger than 4.";
		}
		return ""; //used to indicate that a valid input was returned
	}
	public String validateZipcode() throws NullPointerException {
		if (zipcode.length() != 5 && zipcode.length() != 9) {
			return "Please enter a valid zip code";
		}
		int numZipcode = 0;
		try {
			numZipcode = Integer.parseInt(zipcode);
		} catch(NumberFormatException e) {
			return "A zip code may only contain numbers and such.";
		}
		if (numZipcode > 99950||numZipcode < 501) {
			return "Please enter a valid zip code";
		}
		return ""; //used to indicate that a valid input was returned
	}
	public boolean isFoodAcceptable(String foodType) {
		if (foodType.equals("")) {
			return false;
		}
		for (String preference : foodPreferences) {
			if (foodType.equals(preference)) {
				return false;
			}
		}
		return true;
	}
}
