package com.techelevator.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.restaurant.APIRestaurantDAO;
import com.techelevator.model.restaurant.Restaurant;
import com.techelevator.model.restaurant.RestaurantDAO;
import com.techelevator.security.PasswordHasher;

@Component
public class JDBCUserDAO implements UserDAO {

	private JdbcTemplate jdbcTemplate;
	private PasswordHasher hashMaster;

	@Autowired
	public JDBCUserDAO(DataSource dataSource, PasswordHasher hashMaster) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.hashMaster = hashMaster;
	}
	
	@Override
	public void saveUser(String userName, String password, String email) throws DuplicateKeyException {
		byte[] salt = hashMaster.generateRandomSalt();
		String hashedPassword = hashMaster.computeHash(password, salt);
		String saltString = new String(Base64.encode(salt));
		
		jdbcTemplate.update("INSERT INTO app_users (user_name, password, email, salt, role) VALUES (?, ?, ?, ?, 'user')",
			userName, hashedPassword, email, saltString);
	}

	@Override
	public boolean isPasswordCorrect(String userName, String password) {
		String sqlSearchForUser = "SELECT * "+
							      "FROM app_users "+
							      "WHERE UPPER(user_name) = ? ";
		
		SqlRowSet user = jdbcTemplate.queryForRowSet(sqlSearchForUser, userName.toUpperCase());
		if(user.next()) {
			String dbSalt = user.getString("salt");
			String dbHashedPassword = user.getString("password");
			String givenPassword = hashMaster.computeHash(password, Base64.decode(dbSalt));
			return dbHashedPassword.equals(givenPassword);
		} else {
			return false;
		}
	}

	@Override
	public void updatePassword(String username, String password) {
		byte[] salt = hashMaster.generateRandomSalt();
		String hashedPassword = hashMaster.computeHash(password, salt);
		String saltString = new String(Base64.encode(salt));
		
		jdbcTemplate.update("UPDATE app_users SET password = ? WHERE user_name = ?", hashedPassword, username);
		jdbcTemplate.update("UPDATE app_users SET salt = ? WHERE user_name = ?", saltString, username);
	}

	@Override
	public User getUserByUsername(String userName) {
		String sqlSearchForUsername ="SELECT * "+
		"FROM app_users "+
		"WHERE UPPER(user_name) = ? ";

		SqlRowSet user = jdbcTemplate.queryForRowSet(sqlSearchForUsername, userName.toUpperCase()); 
		User thisUser = null;
		if(user.next()) {
			thisUser = mapRowToUser(user);
		}

		return thisUser;
	}

	@Override
	public User getUserById(int id) {
		String sqlSearchId = "select * from app_users where id=?";
		
		SqlRowSet user = jdbcTemplate.queryForRowSet(sqlSearchId,id);
		User thisUser = null;
		if (user.next()) {
			thisUser = mapRowToUser(user);
		}
		
		return thisUser;
	}

	@Override
	public void deleteUser(String username) {
		String sqlDeleteUser = "DELETE FROM app_users WHERE user_name = ?";
		jdbcTemplate.update(sqlDeleteUser,username);
	}

	@Override
	public void addFoodPreference(int userId, String foodType) {
		String sqlAddFood = "INSERT INTO food_user(food_type,user_id) VALUES(?, ?)";
		jdbcTemplate.update(sqlAddFood,foodType,userId);
	}

	@Override
	public void removeFoodPreference(int userId, String foodType) {
		String sqlGetFoodType = "SELECT * FROM food_user WHERE user_id = ? AND food_type = ?";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlGetFoodType,userId,foodType);
		if (rowReturned.next()) {
			String sqlDeleteFood = "DELETE FROM food_user WHERE user_id = ? AND food_type = ?";
			jdbcTemplate.update(sqlDeleteFood,userId,foodType);
		}
		
	}
	
	@Override
	public List<String> getUserFoodPreferences(int userId) {
		List<String> preferences = new ArrayList<String>();
		String sqlGetPreferences = "SELECT food_type FROM food_user WHERE user_id = ?";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlGetPreferences,userId);
		while (rowsReturned.next()) {
			preferences.add(rowsReturned.getString("food_type"));
		}
		return preferences;
	}

	@Override
	public void updateZipcode(String username, String zipcode) {
		String sqlUpdateZip = "UPDATE app_users SET zipcode = ? WHERE user_name= ?";
		jdbcTemplate.update(sqlUpdateZip,zipcode,username);
	}

	@Override
	public void updateBudget(String username, int budget) {
		String sqlUpdateBudget = "UPDATE app_users SET budget = ? WHERE user_name= ?";
		jdbcTemplate.update(sqlUpdateBudget,budget,username);
	}
	
	private User mapRowToUser(SqlRowSet rowReturned) {
		User theUser = new User();
		
		theUser.setId(rowReturned.getInt("id"));
		theUser.setBudget(rowReturned.getInt("budget"));
		theUser.setPassword(rowReturned.getString("password"));
		theUser.setRole(rowReturned.getString("role"));
		theUser.setUserName(rowReturned.getString("user_name"));
		theUser.setZipcode(rowReturned.getString("zipcode"));
		theUser.setEmail(rowReturned.getString("email"));
		
		return theUser;
	}

	@Override
	public void updateEmail(String username, String email) {
		String sqlUpdateEmail = "UPDATE app_users SET email = ? WHERE user_name= ?";
		jdbcTemplate.update(sqlUpdateEmail,email,username);
	}
	
	@Override
	public void addFavoriteRestaurant(int userId, String restaurantId) {
		//First, we want to see if this restaurant is already in the database
		String sqlRestaurantExists = "SELECT * FROM user_restaurant WHERE user_id = ? AND restaurant_id = ?";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlRestaurantExists,userId,restaurantId);
		if (rowReturned.next()) {
			String sqlAddFavorite = "UPDATE user_restaurant SET is_liked = true WHERE user_id = ? AND restaurant_id = ?";
			jdbcTemplate.update(sqlAddFavorite,userId,restaurantId);
		} else {
			String sqlAddFavorite = "INSERT INTO user_restaurant(user_id,restaurant_id,is_liked) VALUES(?,?,true)";
			jdbcTemplate.update(sqlAddFavorite,userId,restaurantId);
		}
	}

	@Override
	public List<String> getFavoriteRestaurants(int userId) {
		String sqlGetFavorites = "SELECT restaurant_id FROM user_restaurant WHERE user_id = ? AND is_liked = true";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlGetFavorites,userId);
		List<String> restaurantIds = new ArrayList<String>();
		while (rowsReturned.next()) {
			restaurantIds.add(rowsReturned.getString("restaurant_id"));
		}
		return restaurantIds;
	}

	@Override
	public void unfavoriteRestaurant(int userId, String restaurantId) {
		String sqlUnfavorRestaurant = "UPDATE user_restaurant SET is_liked = false WHERE user_id = ? AND restaurant_id = ?";
		jdbcTemplate.update(sqlUnfavorRestaurant,userId,restaurantId);
		
	}

	@Override
	public boolean isRestaurantFavorite(int userId, String restaurantId) {
		String sqlIsFavorite = "SELECT is_liked FROM user_restaurant WHERE user_id = ? AND restaurant_id = ?";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlIsFavorite,userId,restaurantId);
		if (rowReturned.next()) {
			return rowReturned.getBoolean("is_liked");
		}
		return false;
	}

}
