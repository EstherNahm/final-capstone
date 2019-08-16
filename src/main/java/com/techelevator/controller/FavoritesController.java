package com.techelevator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techelevator.model.restaurant.APIRestaurantDAO;
import com.techelevator.model.restaurant.Restaurant;
import com.techelevator.model.restaurant.RestaurantDAO;
import com.techelevator.model.user.User;
import com.techelevator.model.user.UserDAO;
import com.techelevator.visit.Visit;
import com.techelevator.visit.VisitDAO;

@Controller
public class FavoritesController {
	
	private UserDAO userDAO;
	private RestaurantDAO restaurantDAO;
	private VisitDAO visitDAO;
	
	@Autowired
	public FavoritesController(UserDAO userDAO, VisitDAO visitDAO) {
		this.userDAO = userDAO;
		this.visitDAO = visitDAO;
		restaurantDAO = new APIRestaurantDAO();
	}

	@RequestMapping(path="/submitFavorite")
	public String addFavoriteRestaurant(HttpSession session) {
		User user = (User)session.getAttribute("currentUser");
		Restaurant restaurant = (Restaurant)session.getAttribute("currentRestaurant");
		userDAO.addFavoriteRestaurant(user.getId(), restaurant.getId());
		return "redirect:/details";
	}
	
	@RequestMapping(path="/viewFavorites")
	public String viewFavoriteRestaurants(HttpSession session, ModelMap modelHolder) {
		User user = (User)session.getAttribute("currentUser");
		List<String> restaurantIds = userDAO.getFavoriteRestaurants(user.getId());
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Map<String,List<Visit>> allReviews = new HashMap<String,List<Visit>>();
		for (String id : restaurantIds) {  //Get all the reviews for each restaurant and store it in the map
			restaurants.add(restaurantDAO.getRestaurantById(id));
			List<Visit> reviews = visitDAO.getVisitsForRestaurant(id);
			for (int i = 0; i < reviews.size(); i++) {
				Visit aVisit = reviews.get(i);
				if (!visitDAO.visitHasReview(aVisit.getVisitId())) {
					reviews.remove(i);
					i--;
					continue;
				}
				aVisit.setUsername(userDAO.getUserById(aVisit.getUserId()).getUsername());
				aVisit.setIsUsersReview(user.getId());
			}
			allReviews.put(id,reviews);
		}
		modelHolder.addAttribute("restaurants",restaurants);
		modelHolder.addAttribute("reviews",allReviews);
		return "favorites";
	}
	
	@RequestMapping(path="/deleteFavorite")
	public String deleteFavoriteRestaurant(HttpSession session, @RequestParam String restaurantId) {
		User user = (User)session.getAttribute("currentUser");
		userDAO.unfavoriteRestaurant(user.getId(), restaurantId);
		return "redirect:/viewFavorites";
	}
}
