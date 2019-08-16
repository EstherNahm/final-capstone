package com.techelevator.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
public class UserController {

	private UserDAO userDAO;
	private RestaurantDAO restaurantDAO = new APIRestaurantDAO();

	@Autowired
	public UserController(UserDAO userDAO,RestaurantDAO restaurantDAO) {
		this.userDAO = userDAO;
	}

	@RequestMapping(path="/register", method=RequestMethod.GET)
	public String displayNewUserForm(ModelMap modelHolder) {
		if (!modelHolder.containsAttribute("usernameValidation")) {
			modelHolder.addAttribute("passwordValidation","");
			modelHolder.addAttribute("usernameValidation","");
		}
		return "register";
	}
	
	
	
	@RequestMapping(path="/register", method=RequestMethod.POST)
	public String saveUser(@RequestParam String username, 
			@RequestParam String password, 
			@RequestParam String email,
			@RequestParam String confirmPassword, 
			RedirectAttributes flash,
			HttpSession session) {
		
		Boolean isDataValid = true;
		User user = new User();
		user.setPassword(password);
		user.setUserName(username);
		user.setConfirmPassword(confirmPassword);
		user.setFoodPreferences(new ArrayList<String>());
		String passwordValidation = user.validatePassword();
		if (!passwordValidation.equals("")) {
			isDataValid = false;
		}
		String usernameValidation = user.validateUsername();
		if (!usernameValidation.equals("")) {
			isDataValid = false;
		}
		if (isDataValid) {
			try{
				userDAO.saveUser(username, password, email);
			}
			catch(DuplicateKeyException e) {
				usernameValidation = "Sorry, another user with that username already exists.";
				isDataValid = false;
			}
		}
		flash.addFlashAttribute("passwordValidation",passwordValidation);
		flash.addFlashAttribute("message", "You are now registered!");
		flash.addFlashAttribute("usernameValidation",usernameValidation);
		if (!isDataValid) {
			return "redirect:/register";
		}
		user = userDAO.getUserByUsername(user.getUserName());
		user.setFoodPreferences(userDAO.getUserFoodPreferences(user.getId()));
		session.setAttribute("currentUser",user);
		return "redirect:/preferences";
	}
	
//	@RequestMapping(path = "/preferences", method = RequestMethod.POST)
//	public String submitPreferences(@RequestParam(required=false) String zipcode,
//			@RequestParam(required=false) int budget, RedirectAttributes flash,
//			HttpSession session) {
//		System.out.println("Well you got this far, good job, kid, good job!");
//		User user = new User();
//		user.setZipcode(zipcode);
//		String validateZipcode = user.validateZipcode();
//		if (!validateZipcode.equals("")) {
//			return "preferences";
//		} else {
//			User currentUser = (User) session.getAttribute("currentUser");
//			currentUser.setZipcode(zipcode);
//			flash.addFlashAttribute("message","Preferences successfully updated!");
//			userDAO.updateZipcode(currentUser.getUsername(), zipcode);
//		}
//		return "redirect:/recommended";
//	}
	
	
}

