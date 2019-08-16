package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.restaurant.Restaurant;
import com.techelevator.model.user.JDBCUserDAO;
import com.techelevator.model.user.User;
import com.techelevator.model.user.UserDAO;
import com.techelevator.security.PasswordHasher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;


public class TestUserDAO extends DAOIntegrationTest {
	
	private UserDAO dao;
	
    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        PasswordHasher hashMaster = new PasswordHasher();
        dao = new JDBCUserDAO(dataSource, hashMaster);
    }
    @Test
    public void testCreateAndFindUser() {
    	String username = "weathermanDan";
    	String password = "uwutm8";
    	String email = "no@fake.com";
    	dao.saveUser(username, password,email);
    	User testUsername = dao.getUserByUsername(username);
    	User testUserid = dao.getUserById(testUsername.getId());
    	assertEquals("Usernames aren't equal",testUsername.getUserName(),testUserid.getUserName());
    	assertEquals("Ids aren't equal",testUsername.getId(),testUserid.getId());
    	assertEquals("Passwords aren't equal",testUsername.getPassword(),testUserid.getPassword());
    	assertTrue("OMG WRONG PASSWORD SCRUB!!!",dao.isPasswordCorrect(username, password));
    	password = "uwotm8";
    	dao.updatePassword(username, password);
    	testUsername = dao.getUserByUsername(username);
    	assertTrue("Can't update passwords, got " + testUsername.getPassword() + " for " + testUsername.getUserName(),dao.isPasswordCorrect(username, password));
    }
    
    @Test
    public void testDeleteUser() {
    	String username = "weathermanDan";
    	dao.saveUser(username, "imtoolazytomakeafakepassword","fakeEmail@gotyou.com");
    	User testUser = dao.getUserByUsername(username);
    	assertTrue("Didn't actually create a user whoa",testUser.getUserName().equals(username));
    	dao.deleteUser(username);
    	testUser = dao.getUserByUsername(username);
    	assertTrue("Got a user that should have been deleted",testUser == null);
    }
    
    @Test
    public void testUserFavorites() {
    	String username = "queenCersei";
    	dao.saveUser(username, "IHeartMyBrother","fakeemail@igotyou.com");
    	User queenCersei = dao.getUserByUsername(username);
    	int userId = queenCersei.getId();
    	String restaurantId = "vs8orAK8nDXFxFiIfv0yYQ"; //id for gina's pizza
    	dao.addFavoriteRestaurant(userId, restaurantId);
    	List<String> favoriteRestaurants = dao.getFavoriteRestaurants(userId);
    	assertEquals("Wrong number of restaurants returned",1,favoriteRestaurants.size());
    	boolean testStatement = favoriteRestaurants.get(0).equals(restaurantId);
    	assertTrue("Wrong restaurant added",testStatement);
    	restaurantId = "Ra5fI9BEAuWWaLJSfkiCiQ"; //id for White Oaks
    	dao.addFavoriteRestaurant(userId, restaurantId);
    	favoriteRestaurants = dao.getFavoriteRestaurants(userId);
    	assertEquals("Cannot add additional restaurants",2,favoriteRestaurants.size());
    	testStatement = favoriteRestaurants.get(1).equals(restaurantId);
    	assertTrue("Wrong restaurant added",testStatement);
    }
    
    @Test
    public void testUserFoodPreferences() {
    	String username = "ronSwanson";
    	dao.saveUser(username, "GoAmurrika","dont@emailme.com");
    	User ronSwanson = dao.getUserByUsername(username);
    	dao.addFoodPreference(ronSwanson.getId(), "meat");
    	List<String> americanFood = dao.getUserFoodPreferences(ronSwanson.getId());
    	assertEquals("Got the wrong number of food preferences",1,americanFood.size());
    	boolean testStatement = americanFood.get(0).equals("meat");
    	assertTrue("Didn't get meat back",testStatement);
    	dao.addFoodPreference(ronSwanson.getId(),"Freedom!");
    	americanFood = dao.getUserFoodPreferences(ronSwanson.getId());
    	assertEquals("Could not add additional food to preferences",2,americanFood.size());
    	testStatement = americanFood.get(1).equals("Freedom!");
    	assertTrue("Didn't get freedom back",testStatement);
    	
    }

}
