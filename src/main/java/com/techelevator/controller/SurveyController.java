package com.techelevator.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
public class SurveyController {

	@Autowired
	private VisitDAO visitDao;

	@Autowired
	private RestaurantDAO restaurantDao;

	@Autowired
	private UserDAO userDao;
	
	

	@RequestMapping(path = "/saveVisit", method = RequestMethod.POST)
	public String saveVisit(ModelMap modelHolder, HttpSession session) {

		Restaurant thisRestaurant = (Restaurant) session.getAttribute("currentRestaurant");
		String restaurantId = thisRestaurant.getId();
		User thisUser = (User) session.getAttribute("currentUser");
		int userId = thisUser.getId();

		Visit aVisit = new Visit();
		aVisit.setUserId(userId);
		aVisit.setRestaurantId(restaurantId);

		List<Visit> reviews = visitDao.getVisitsForRestaurant(thisRestaurant.getId());
		for (int i = 0; i < reviews.size(); i++) {
			aVisit = reviews.get(i);
			if (!visitDao.visitHasReview(aVisit.getVisitId())) {
				reviews.remove(i);
				i--;
				continue;
			}
		}

		session.setAttribute("tempSave", aVisit);

		modelHolder.addAttribute("Restaurant", thisRestaurant);
		modelHolder.addAttribute("User", thisUser);

		return "redirect:/details";

	}
	
	@RequestMapping(path = "/saveFinalVisit", method = RequestMethod.POST)
	public String saveFinalVisit(ModelMap modelHolder, HttpSession session) {

		Restaurant thisRestaurant = (Restaurant) session.getAttribute("currentRestaurant");
		String restaurantId = thisRestaurant.getId();
		User thisUser = (User) session.getAttribute("currentUser");
		int userId = thisUser.getId();

		Visit aVisit = new Visit();
		aVisit.setUserId(userId);
		aVisit.setRestaurantId(restaurantId);



		visitDao.saveVisit(userId, restaurantId);

		modelHolder.addAttribute("Restaurant", thisRestaurant);
		modelHolder.addAttribute("User", thisUser);

		return "redirect:/details";

	}

	@RequestMapping(path = "/submit_review", method = RequestMethod.POST)
	public String saveSurvey(ModelMap modelHolder, @RequestParam int rating, @RequestParam String text,
			HttpSession session, @RequestParam(required=false) String destination,
			@RequestParam(required=false) String restaurantId) {

		String userText = text;
		int starRating = rating;
		User thisUser = (User) session.getAttribute("currentUser");
		if (restaurantId == null) {
			Restaurant restaurant = (Restaurant) session.getAttribute("currentRestaurant");
			restaurantId = restaurant.getId();
		}
		Restaurant thisRestaurant = restaurantDao.getRestaurantById(restaurantId);
		int userId = thisUser.getId();
		visitDao.saveVisit(userId, restaurantId);
		int visitId = visitDao.getMostRecentVisit(thisUser.getId()).getVisitId();

		try {
			visitDao.updateReview(visitId, userText, starRating);
			if (starRating == 0) {
				System.out.println("you need to select your star rating");
			}
		} catch (NullPointerException e) {
			if (destination == null) {
				return "redirect:/details";
			} else {
				return destination;
			}
		}

		modelHolder.addAttribute("User", thisUser);
		modelHolder.addAttribute("Restaurant", thisRestaurant);
		modelHolder.addAttribute("rating", starRating);
		modelHolder.addAttribute("userText", userText);
		modelHolder.addAttribute("thisVisitId", visitId);

		if (destination == null) {
			return "redirect:/details";
		} else {
			return destination;
		}

	}

	@RequestMapping("/showAllSurveys")
	public String showAllSurveys(RedirectAttributes flash, HttpSession session) {

		Restaurant thisRestaurant = (Restaurant) session.getAttribute("currentRestaurant");
		List<Visit> surveyList = visitDao.getVisitsForRestaurant(thisRestaurant.getId());

		for (Visit aVisit : surveyList) {
			aVisit.setUsername(userDao.getUserById(aVisit.getUserId()).getUsername());
		}

		flash.addFlashAttribute("restaurants", thisRestaurant);
		flash.addFlashAttribute("surveyList", surveyList);

		return "redirect:/details";
	}

	@RequestMapping("/deleteReview")
	public String deleteReview(@RequestParam int visitId, @RequestParam(required=false) String destination) {
		visitDao.deleteReview(visitId);
		if (destination == null) {
			return "redirect:/details";
		}
		return destination;
	}
	
	@RequestMapping("/deleteVisit")
	public String deleteVisit(@RequestParam String restaurantId, HttpSession session) {
		User user = (User)session.getAttribute("currentUser");
		int userId = user.getId();
		Visit visit = visitDao.getMostRecentVisit(userId, restaurantId);
		int visitId = visit.getVisitId();
		visitDao.deleteVisit(visitId);
		return "redirect:/visited";
	}

}
