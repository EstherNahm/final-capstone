package com.techelevator.visit;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCVisitDAO implements VisitDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCVisitDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void saveVisit(int userId, String restaurantId ) {
		String sqlSaveVisit = "INSERT INTO visit(user_id,restaurant_id,has_been_asked_about) values(?,?,false)";
		jdbcTemplate.update(sqlSaveVisit,userId,restaurantId);
	}
	
	@Override
	public void saveVisit(int userId, String restaurantId, LocalDate dateOfVisit) {
		String sqlSaveVisit = "INSERT INTO visit(user_id,restaurant_id,date,has_been_asked_about) values(?,?,?,false)";
		jdbcTemplate.update(sqlSaveVisit,userId,restaurantId,dateOfVisit);
	}

	@Override
	public void updateReview(int visitId, String text, int rating) {
		String sqlUpdateReview = "UPDATE visit SET review = ?, rating = ?, has_been_asked_about = true WHERE visit_id = ?";
		jdbcTemplate.update(sqlUpdateReview,text,rating,visitId);
	}

	@Override
	public List<Visit> searchForVisits(int userId, String restaurantId) {
		List<Visit> Visits = new ArrayList<Visit>();
		String sqlSearchVisit = "select * from visit where user_id = ? and restaurant_id = ?";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlSearchVisit,userId,restaurantId);
		while (rowReturned.next()) {
			Visits.add(mapRowToVisit(rowReturned));
		}
		return Visits;
	}

	@Override
	public Visit getVisitById(int visitId) {
		Visit aVisit = new Visit();
		String sqlSearchById = "select * from visit where visit_id = ?";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlSearchById,visitId);
		if (rowReturned.next()) {
			aVisit = mapRowToVisit(rowReturned);
		}
		return aVisit;
	}

	@Override
	public List<Visit> getVisitsForRestaurant(String restaurantId) {
		List<Visit> visits = new ArrayList<Visit>();
		String sqlGetRestaurantVisits = "select * from visit where restaurant_id = ? ORDER BY date DESC, visit_id DESC";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlGetRestaurantVisits,restaurantId);
		while (rowsReturned.next()) {
			visits.add(mapRowToVisit(rowsReturned));
		}
		return visits;
	}

	@Override
	public List<Visit> getVisitsForUser(int userId) {
		List<Visit> visits = new ArrayList<Visit>();
		String sqlGetRestaurantVisits = "select * from visit where user_id = ? ORDER BY date DESC";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlGetRestaurantVisits,userId);
		while (rowsReturned.next()) {
			visits.add(mapRowToVisit(rowsReturned));
		}
		return visits;
	}
	
	private Visit mapRowToVisit(SqlRowSet rowReturned) {
		Visit aVisit = new Visit();
		aVisit.setRating(rowReturned.getInt("rating"));
		aVisit.setRestaurantId(rowReturned.getString("restaurant_id"));
		aVisit.setReview(rowReturned.getString("review"));
		aVisit.setUserId(rowReturned.getInt("user_id"));
		aVisit.setVisitId(rowReturned.getInt("visit_id"));
		LocalDate date = rowReturned.getDate("date").toLocalDate();
		aVisit.setDateOfVisit(date);
		return aVisit;
	}

	@Override
	public double getAverageRating(String restaurantId) {
		List<Visit> allReviews = getVisitsForRestaurant(restaurantId);
		int totalReviews = 0;
		int totalRating = 0;
		for (Visit aVisit : allReviews) {
			if (visitHasReview(aVisit.getVisitId())) {
				totalRating += aVisit.getRating();
				totalReviews += 1;
			}
		}
		return (double)totalRating/totalReviews;
	}

	@Override
	public Visit getMostRecentVisit(int userId) {
		Visit aVisit = null;
		String sqlGetVisit = "SELECT * from visit WHERE user_id = ?" 
				+ " ORDER BY date desc, visit_id desc LIMIT 1";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlGetVisit,userId);
		if (rowReturned.next()) {
			aVisit = mapRowToVisit(rowReturned);
		}
		return aVisit;
	}
	
	@Override
	public Visit getMostRecentVisit(int userId, String restaurantId) {
		Visit aVisit = new Visit();
		String sqlGetVisit = "SELECT * FROM visit WHERE user_id = ? AND restaurant_id = ?" 
				+ " ORDER BY date DESC, visit_id DESC LIMIT 1";
		//SELECT * FROM visit WHERE user_id = 2 AND restaurant_id = '7hIl2AOZl6Zfr2fAojpeRw'
		// ORDER BY date DESC, visit_id DESC LIMIT 1;
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlGetVisit,userId,restaurantId);
		if (rowReturned.next()) {
			aVisit = mapRowToVisit(rowReturned);
		}
		return aVisit;
	}

	@Override
	public boolean visitHasReview(int visitId) {
		String sqlVisitReview = "SELECT * FROM visit WHERE visit_id = ? AND review is not null";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlVisitReview,visitId);
		if (rowsReturned.next()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasBeenAskedABout(int visitId) {
		String sqlHasBeen = "SELECT has_been_asked_about FROM visit WHERE visit_id = ?";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlHasBeen,visitId);
		if (rowReturned.next()) {
			return rowReturned.getBoolean("has_been_asked_about");
		}
		return false;
	}

	@Override
	public void askAbout(int visitId) {
		String sqlAskAbout = "UPDATE visit SET has_been_asked_about = true WHERE visit_id = ?";
		jdbcTemplate.update(sqlAskAbout,visitId);
		
	}

	@Override
	public void deleteReview(int visitId) {
		String sqlDeleteReview = "UPDATE visit SET review = null WHERE visit_id = ?";
		jdbcTemplate.update(sqlDeleteReview,visitId);
	}

	@Override
	public void deleteVisit(int visitId) {
		String sqlDeleteVisit = "DELETE FROM visit WHERE visit_id = ?";
		jdbcTemplate.update(sqlDeleteVisit,visitId);
	}
	
	

}
