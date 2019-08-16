package com.techelevator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.controller.ExceptionHandler.RestaurantNotFoundException;
import com.techelevator.model.restaurant.APIRestaurantDAO;
import com.techelevator.model.restaurant.DailyAvailability;
import com.techelevator.model.restaurant.Restaurant;
import com.techelevator.model.restaurant.RestaurantDAO;
import com.techelevator.model.user.User;
import com.techelevator.model.user.UserDAO;
import com.techelevator.visit.Visit;
import com.techelevator.visit.VisitDAO;

@Controller
@RequestMapping
public class RestaurantController {

	private RestaurantDAO restaurantDao;
	private Restaurant restaurant;

	private VisitDAO visitDAO;
	private Visit visit;

	private UserDAO userDAO;
	private User user;

	public RestaurantController(UserDAO userDAO, VisitDAO visitDAO) {
		this.userDAO = userDAO;
		this.visitDAO = visitDAO;
		this.restaurantDao = new APIRestaurantDAO();
	}
	
	//When starting a new search, make sure no previous restaurants are stored in the session
	@RequestMapping(path= {"/getMatched"})
	public String invalidatePrevRestaurants(HttpSession session) {
		session.setAttribute("prevRestaurants",null);
		return "redirect:/recommended";
	}

	@RequestMapping(path = { "/recommended" })
	public String showRestaurant(ModelMap modelHolder, HttpSession session, RedirectAttributes flash) {
		Stack<String> prevRestaurants = (Stack<String>) (session.getAttribute("prevRestaurants"));
		if (prevRestaurants == null) {
			prevRestaurants = new Stack<String>();
			modelHolder.addAttribute("hasPrevious", false);
		} else {
			modelHolder.addAttribute("hasPrevious", true);
		}

		try {
			restaurant = restaurantDao.getRandomishRestaurant((User) session.getAttribute("currentUser"),
					prevRestaurants);
		} catch (IllegalArgumentException e) { // This happens if the user dislikes all the restaurants in their search
			List<String> emptyList = new ArrayList<String>();
			session.setAttribute("dislikeList", emptyList);
			restaurant = restaurantDao.getRandomishRestaurant((User) session.getAttribute("currentUser"),
					emptyList);
		} catch (RuntimeException e) { // This could also happen if the user dislikes all the restaurants in their
										// search
			List<String> emptyList = new ArrayList<String>();
			session.setAttribute("dislikeList", emptyList);
			flash.addFlashAttribute("validateZipcode", "We couldn't find any restaurants for your search. "
					+ "Please enter a new zip code, a new budget, or a new food preference");
			return "redirect:/preferences";
		}

		prevRestaurants.add(restaurant.getId());
		session.setAttribute("prevRestaurants", prevRestaurants);

		modelHolder.addAttribute("Restaurant", restaurant);
		session.setAttribute("currentRestaurant", restaurant);
		
		int avgRating = (int)Math.ceil(visitDAO.getAverageRating(restaurant.getId()));
		modelHolder.addAttribute("avgRating",avgRating);
		
		User user = (User)session.getAttribute("currentUser");
		boolean isLiked = userDAO.isRestaurantFavorite(user.getId(), restaurant.getId());
		modelHolder.addAttribute("isLiked",isLiked);
		
		return "recommended";

	}

	@RequestMapping(path = { "/details" })
	public String detailsRestaurant(ModelMap modelHolder, HttpSession session,
			@RequestParam(required=false) String restaurantId, @RequestParam(required=false) String showAllReviews) {
		Restaurant restaurant = null;
		if (restaurantId == null) {
			restaurant = (Restaurant)session.getAttribute("currentRestaurant");
		} else {
			restaurant = restaurantDao.getRestaurantById(restaurantId);
		}
		
		User user = (User)session.getAttribute("currentUser");
		
		List<DailyAvailability> hoursOpen = restaurantDao.getHoursOpen(restaurant.getId());

		restaurant.setHours(hoursOpen);
		modelHolder.addAttribute("Restaurant", restaurant);
		session.setAttribute("currentRestaurant", restaurant);

		List<Visit> surveyList = visitDAO.getVisitsForRestaurant(restaurant.getId());
		List<Visit> shortList = new ArrayList<Visit>();  //we only want the thing to display the first five restaurants
		
		for (int i = 0; i < surveyList.size(); i++) {
			Visit aVisit = surveyList.get(i);
			if (!visitDAO.visitHasReview(aVisit.getVisitId())) {
				surveyList.remove(i);
				i--;
			} else {
				//Username cannot be added to visit in the JDBC and has to be added here
				aVisit.setUsername(userDAO.getUserById(aVisit.getUserId()).getUsername());
				aVisit.setIsUsersReview(user.getId());
				if (i < 5) {
					shortList.add(aVisit);
				}
			}
		}

		if (showAllReviews == null) {
			modelHolder.addAttribute("surveyList",shortList);
		} else {
			modelHolder.addAttribute("surveyList", surveyList);
		}
		
		
		int avgRating = (int)Math.ceil(visitDAO.getAverageRating(restaurant.getId()));
		modelHolder.addAttribute("avgRating", avgRating);

		return "details";
	}

	@RequestMapping(path = "/goBack")
	public String showPreviousRestaurant(HttpSession session, ModelMap modelHolder) {
		Stack<String> prevRestaurants = (Stack<String>) session.getAttribute("prevRestaurants");
		prevRestaurants.pop();
		String restaurantId = prevRestaurants.peek();
		Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);
		modelHolder.addAttribute("Restaurant", restaurant);
		session.setAttribute("currentRestaurant", restaurant);

		if (prevRestaurants.size() > 1) {
			modelHolder.addAttribute("hasPrevious", true);
		} else {
			modelHolder.addAttribute("hasPrevious", false);
		}
		
		int avgRating = (int)Math.floor(visitDAO.getAverageRating(restaurant.getId()));
		modelHolder.addAttribute("avgRating",avgRating);
		
		User user = (User)session.getAttribute("currentUser");
		boolean isLiked = userDAO.isRestaurantFavorite(user.getId(), restaurant.getId());
		modelHolder.addAttribute("isLiked",isLiked);
		
		return "recommended";
	}

	@RequestMapping(path = "/return")
	public String returnToRecommended(HttpSession session, ModelMap modelHolder) {
		//Stack<String> prevRestaurants = (Stack<String>) session.getAttribute("prevRestaurants");
		//String restaurantId = prevRestaurants.peek();
		//Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);
		Restaurant restaurant = (Restaurant)session.getAttribute("currentRestaurant");
		modelHolder.addAttribute("Restaurant", restaurant);
		session.setAttribute("currentRestaurant", restaurant);

//		if (prevRestaurants.size() > 1) {
//			modelHolder.addAttribute("hasPrevious", true);
//		} else {
//			modelHolder.addAttribute("hasPrevious", false);
//		}
		
		int avgRating = (int)Math.floor(visitDAO.getAverageRating(restaurant.getId()));
		modelHolder.addAttribute("avgRating",avgRating);
		
		User user = (User)session.getAttribute("currentUser");
		boolean isLiked = userDAO.isRestaurantFavorite(user.getId(), restaurant.getId());
		modelHolder.addAttribute("isLiked",isLiked);
		
		return "recommended";
	}
//	@RequestMapping("/{id}")
//	public Restaurant read(@PathVariable String id) throws RestaurantNotFoundException {
//		Restaurant restaurant = restaurantDao.getRestaurantById("bupMXFUaZfranBLdaVcRww");
//		if (restaurant != null) {
//			return restaurant;
//		} else {
//			throw new RestaurantNotFoundException(id, "Restaurant Not Found!");
//		}
//	}

}