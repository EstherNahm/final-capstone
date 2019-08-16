package com.techelevator;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.visit.JDBCVisitDAO;
import com.techelevator.visit.Visit;
import com.techelevator.visit.VisitDAO;

public class TestVisitDAO extends DAOIntegrationTest {
	
	private VisitDAO dao;
	
	@Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        dao = new JDBCVisitDAO(dataSource);
    }

	@Test
	public void testAddVisit() {
		int userId = 2; //When I made this test, this was the ID of user Martin
		String restaurantId = "-mP3F3srknwKJdJ5FqcX5Q"; //id of Mitchell's Ice Cream
		dao.saveVisit(userId, restaurantId);
		List<Visit> Visits = dao.searchForVisits(userId, restaurantId);
		assertEquals("Wrong number of visits received",1,Visits.size());
		assertNotEquals("Your visit is null",null,Visits.get(0));
		LocalDate currentDate = LocalDate.now();
		dao.saveVisit(userId, restaurantId, currentDate);
		Visits = dao.searchForVisits(userId, restaurantId);
		assertEquals("Wrong number of visits recieved",2,Visits.size());
		assertNotEquals("Your second visit is null",null,Visits.get(1));
		int visitId = Visits.get(0).getVisitId();
		Visit aVisit = dao.getVisitById(visitId);
		boolean testStatement = aVisit.getRestaurantId().contentEquals(restaurantId);
		assertTrue("Can't get the right visit by ID",testStatement);
		String review =  "What are you looking at, punk?";
		int rating = 5; //!!!
		dao.updateReview(visitId, review, rating);
		aVisit = dao.getVisitById(visitId);
		testStatement = aVisit.getReview().equals(review);
		assertTrue("Review updated improperly",testStatement);
		assertEquals("Rating updated improperly",rating,aVisit.getRating());
	}

}
