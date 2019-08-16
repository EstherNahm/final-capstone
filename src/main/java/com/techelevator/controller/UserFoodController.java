package com.techelevator.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.restaurant.APIRestaurantDAO;
import com.techelevator.model.restaurant.RestaurantDAO;
import com.techelevator.model.user.User;
import com.techelevator.model.user.UserDAO;

@Controller
public class UserFoodController {

	private UserDAO userDAO;
	private RestaurantDAO restaurantDAO;

	@Autowired
	public UserFoodController(UserDAO userDAO) {
		this.userDAO = userDAO;
		restaurantDAO = new APIRestaurantDAO();
	}

	@RequestMapping(path = "/preferences", method = RequestMethod.POST)
	public String submitPreferences(
			@RequestParam(required = false) String zipcode,
			@RequestParam(required = false) int budget, 
			@RequestParam(required = false) String foodType,
			RedirectAttributes flash, HttpSession session) {
		boolean formIsValid = true;
		User user = new User();
		User currentUser = (User) session.getAttribute("currentUser");
		if (foodType.length() > 2) {
			String aliasOfFood = restaurantDAO.getMatchingAlias(foodType);
			if (aliasOfFood.equals("failure")) {
				formIsValid = false;
				flash.addFlashAttribute("validateFood", "That food isn't in our database");
			} else {
				List<String> allCategories = userDAO.getUserFoodPreferences(currentUser.getId());
				for (String category : allCategories) {
					userDAO.removeFoodPreference(currentUser.getId(), category);
				}
				List<String> newCategory = new ArrayList<String>();
				newCategory.add(aliasOfFood);
				currentUser.setFoodPreferences(newCategory);
				userDAO.addFoodPreference(currentUser.getId(), aliasOfFood);
			}
		} else if (foodType.length() > 0) {
			flash.addFlashAttribute("validateFood","Your food type must be at least 3 characters");
		}
		user.setZipcode(zipcode);
		String validateZipcode = user.validateZipcode();
		try {
			if (!validateZipcode.equals("")) {
				if (zipcode.equals("") || zipcode == null) {
					try {
						if (currentUser.validateZipcode().equals("")) {
							validateZipcode = "";
						} else {
							formIsValid = false;
							validateZipcode = "Please enter a zip code";
						}
					} catch (NullPointerException e) {
						formIsValid = false;
						validateZipcode = "Please enter a zip code";
					}
				} else {
					formIsValid = false;
				}
				flash.addFlashAttribute("validateZipcode", validateZipcode);
			} else {
				currentUser.setZipcode(zipcode);
				flash.addFlashAttribute("validateZipcode", "Zip code successfully updated!");
				userDAO.updateZipcode(currentUser.getUsername(), zipcode);
			}
			if (budget != 0 && budget != currentUser.getBudget()) {
				currentUser.setBudget(budget);
				flash.addFlashAttribute("validateBudget", "Budget successfully updated!");
				userDAO.updateBudget(currentUser.getUsername(), budget);
			}
			session.setAttribute("currentUser", currentUser);
			if (!formIsValid) {
				return "redirect:/preferences";
			}
			return "redirect:/getMatched";

		}
		// if the user is not logged in, send them to the welcome page
		catch (NullPointerException notLoggedIn) {
			return "welcome";
		}
	}

}
