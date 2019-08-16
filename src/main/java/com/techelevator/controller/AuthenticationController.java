package com.techelevator.controller;

import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.restaurant.APIRestaurantDAO;
import com.techelevator.model.restaurant.Restaurant;
import com.techelevator.model.restaurant.RestaurantDAO;
import com.techelevator.model.user.User;
import com.techelevator.model.user.UserDAO;
import com.techelevator.visit.Visit;
import com.techelevator.visit.VisitDAO;

@Controller
public class AuthenticationController {

	private UserDAO userDAO;
	private VisitDAO visitDao;
	private RestaurantDAO restaurantDao;

	@Autowired
	public AuthenticationController(UserDAO userDAO, VisitDAO visitDao) {
		this.userDAO = userDAO;
		this.visitDao = visitDao;
		restaurantDao = new APIRestaurantDAO();
	}

	@RequestMapping(path= {"/", "/login"}, method=RequestMethod.GET)
	public String displayLoginForm(ModelMap modelHolder, HttpSession session, RedirectAttributes flash, HttpServletRequest request) {
		User thisUser = (User)session.getAttribute("currentUser");
		
	
		modelHolder.addAttribute("currentUser", thisUser);
		return "welcome";
	}
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public String login(ModelMap modelHolder,
						@RequestParam String username, 
						@RequestParam String password, 
						@RequestParam(required=false) String destination,
						HttpSession session, RedirectAttributes flash) {
		if(userDAO.isPasswordCorrect(username, password)) {
			User user = userDAO.getUserByUsername(username);
			user.setFoodPreferences(userDAO.getUserFoodPreferences(user.getId()));
			session.setAttribute("currentUser", user);
			
			
				
				int userId = user.getId();
				
				try {
				Visit aVisit = visitDao.getMostRecentVisit(userId); 
				if(visitDao.hasBeenAskedABout(aVisit.getVisitId()) == false) {
					visitDao.askAbout(aVisit.getVisitId());
					String restaurantId = aVisit.getRestaurantId();
					Restaurant aRestaurant = restaurantDao.getRestaurantById(restaurantId);
					session.setAttribute("currentRestaurant", aRestaurant);
					modelHolder.addAttribute("Restaurant" , aRestaurant);
					
					
					return "feedback";
					
				}
				} catch (NullPointerException e) {
					return "preferences";
				}
				
				
				
			if(destination != null && ! destination.isEmpty()) {
				return "redirect:" + destination;
			} else {
				return "redirect:/preferences";
			}
		} else {
			flash.addFlashAttribute("message","Invalid username/password combination");
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(path="/feedback", method=RequestMethod.POST)
	public String feedbackPage(ModelMap modelHolder,
			HttpServletRequest request,
			@RequestParam(required=false) String yesButton,
			HttpSession session, RedirectAttributes flash) {
		
	
		User thisUser = (User)session.getAttribute("currentUser");
		int userId = thisUser.getId();
		Visit aVisit = visitDao.getMostRecentVisit(userId); 
		visitDao.askAbout(aVisit.getVisitId());
		String restaurantId = aVisit.getRestaurantId();
		Restaurant aRestaurant = restaurantDao.getRestaurantById(restaurantId);
		modelHolder.addAttribute("Restaurant" , aRestaurant);
		
		if (yesButton.equals("0")) {

			System.out.println("button equals yes");

			
			if(visitDao.hasBeenAskedABout(aVisit.getVisitId()) == false) {
				visitDao.askAbout(aVisit.getVisitId());
			}
			
			

			
			return "redirect:/visited";
		}
			
		
		return "redirect:/preferences";
	}
	
	
	@Controller
    public class LogoutController {
		
		@RequestMapping(value="/confirmation",method = RequestMethod.GET)
		public String confirmationPage(HttpServletRequest request, 
				ModelMap modelHolder) {
			
			HttpSession session = request.getSession();
			
			 session.getAttribute("tempSave");
			 if(null == session.getAttribute("tempSave")){
				 return "redirect:/logout";
			 }
			 
			 Restaurant thisRestaurant = (Restaurant)session.getAttribute("currentRestaurant");
				User thisUser = (User)session.getAttribute("currentUser");
				int userId = thisUser.getId();
				
				
				modelHolder.addAttribute("Restaurant", thisRestaurant);
				modelHolder.addAttribute("User", thisUser);
				
			
				
			return "confirmation";
		}
		

        @RequestMapping(value="/logout",method = RequestMethod.GET)
        public String logout(HttpServletRequest request, RedirectAttributes flash){
      
    		HttpSession httpSession = request.getSession();
            httpSession.invalidate();
            flash.addFlashAttribute("message","You have logged out succesfuly. Log back in?");
            
            return "redirect:/";
            
        }
        @RequestMapping(value="/logout",method = RequestMethod.POST)
        public String gettingToVisitRestaurant(ModelMap modelHolder, 
        		@RequestParam String yesButton,
        		HttpSession session, 
        		HttpServletRequest request) {
        	
        	
        	
        	Restaurant thisRestaurant = (Restaurant)session.getAttribute("currentRestaurant");
			String restaurantId = thisRestaurant.getId();
			User thisUser = (User)session.getAttribute("currentUser");
			int userId = thisUser.getId();
   
        	if(yesButton.equals("0")) {
        		visitDao.saveVisit(userId, restaurantId);
        		}
        	
        	 HttpSession httpSession = request.getSession();
             httpSession.invalidate();
             
        	
        	
        	return "redirect:/";
        }
    }

}