package com.techelevator.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.techelevator.model.restaurant.APIRestaurantDAO;
import com.techelevator.model.restaurant.Restaurant;
import com.techelevator.model.restaurant.RestaurantDAO;
import com.techelevator.model.user.User;
import com.techelevator.visit.Visit;
import com.techelevator.visit.VisitDAO;

@Controller
public class NavBarController {
	
	private VisitDAO visitDAO;
	private RestaurantDAO restaurantDAO;
	
	@Autowired
	public NavBarController(VisitDAO visitDAO) {
		this.visitDAO = visitDAO;
		restaurantDAO = new APIRestaurantDAO();
	}

	@RequestMapping(path = {"/preferences"})
	public String showPreferencePage(ModelMap modelHolder, HttpSession session) {
		if (!modelHolder.containsAttribute("validateZipcode")) {
			modelHolder.addAttribute("validateZipcode","");
		}
		if (!modelHolder.containsAttribute("validateBudget")) {
			modelHolder.addAttribute("validateBudget","");
		}
		if (!modelHolder.containsAttribute("validateFood")) {
			modelHolder.addAttribute("validateFood","");
		}
		modelHolder.addAttribute("user",(User) session.getAttribute("currentUser"));
		return "preferences";
	}

	@RequestMapping(path = {"/about"})
	public String showAboutPage() {
	return "about";
	}
	@RequestMapping(path = {"/welcome"})
	public String showWelcomePage() {
	return "welcome";
	}
	
	@RequestMapping(path = {"/feedback"})
	public String showFeedbackPage() {
	return "feedback";
	}
	@RequestMapping(path = {"/confirmation"})
	public String showConformationPage() {
	return "confirmation";
	}
	
	@RequestMapping(path = {"/visited"})
	public String showVisitedPage(HttpSession session, ModelMap modelHolder) {
		User user = (User)session.getAttribute("currentUser");
		int userId = user.getId();
		List<Visit> visits = visitDAO.getVisitsForUser(userId);
		List<String> restaurantIds = new ArrayList<String>();
		for (Visit visit : visits) {
			String restaurantId = visit.getRestaurantId();
			restaurantIds.add(restaurantId);
		}
		
		restaurantIds = filterRedundantRestaurants(restaurantIds);
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		for (String id : restaurantIds) {
			Restaurant aRestaurant = restaurantDAO.getRestaurantById(id);
			restaurants.add(aRestaurant);
		}
		
		modelHolder.addAttribute("restaurants",restaurants);
		return "visited";
	}
	
	//helper method
	private List<String> filterRedundantRestaurants(List<String> filteredList) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < filteredList.size(); i++) {
			String matchId = filteredList.get(i);
			boolean hasMatch = false;
			for (String searchId : ids) {
				if (searchId.equals(matchId)) {
					filteredList.remove(i);
					i--;
					hasMatch = true;
				}
			}
			if (!hasMatch) {
				ids.add(matchId);
			}
		}
		return filteredList;
	}
	
	
	
}
