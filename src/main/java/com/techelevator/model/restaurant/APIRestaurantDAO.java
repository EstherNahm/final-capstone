package com.techelevator.model.restaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.user.JDBCUserDAO;
import com.techelevator.model.user.User;
import com.techelevator.model.user.UserDAO;
import com.techelevator.security.PasswordHasher;

@Component
public class APIRestaurantDAO implements RestaurantDAO {

	private ObjectMapper mapper = new ObjectMapper();
	private List<Category> allYelpCategories;
	private final String API_KEY = "Bearer arO5gMROZbWHpmufZa7ZXNCqNZzKXYalglF-EeUHVcbClZuSt-d54jAHLLjP7q1j1qTQVEdnFCwzV8oyMT7ikXdTJESwt9hVHQJYnVsjI5cpsppj7BtwCvJkKrZVXXYx";

	public List<Category> getAllYelpCategories() {
		allYelpCategories = new ArrayList<Category>();
		try {
			URL url = new URL("https://www.yelp.com/developers/documentation/v3/all_category_list/categories.json");
			String jsonString = getJsonStringFromUrl(url);
			JsonNode jsonNode = mapper.readTree(jsonString);
			Iterator<JsonNode> jsonNodes = jsonNode.elements();
			while (jsonNodes.hasNext()) {
				JsonNode categoryNode = jsonNodes.next();
				Category aCategory = new Category();
				aCategory.setAlias(categoryNode.get("alias").asText());
				aCategory.setTitle(categoryNode.get("title").asText());
				Iterator<JsonNode> parents = categoryNode.get("parents").elements();
				List<String> allParents = new ArrayList<String>();
				boolean shouldAdd = false; //filter out non-restaurants
				while (parents.hasNext()) {
					String thisParent = parents.next().asText();
					allParents.add(thisParent);
					aCategory.setParents(allParents);
					if (thisParent.equals("restaurants")||thisParent.equals("food")) {
						shouldAdd = true;
					}
				}
				if (shouldAdd) {
					allYelpCategories.add(aCategory);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allYelpCategories;
	}

	@Override
	public Restaurant getRestaurantById(String id) {
		Restaurant aRestaurant = new Restaurant();
		JsonNode jsonNode = null;
		try {
			URL url = new URL("https://api.yelp.com/v3/businesses/" + id);
			String jsonString = getJsonStringFromUrl(url);
			jsonNode = mapper.readTree(jsonString);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		aRestaurant = mapNodeToRestaurant(jsonNode);
		return aRestaurant;
	}

	@Override
	public Restaurant getRandomishRestaurant(User user, List<String> dislikedRestaurantIds)
			throws IllegalArgumentException, RuntimeException {
		Restaurant randomRestaurant = new Restaurant();
		List<Restaurant> userRestaurants = getRestaurantsByUserSearch(user);
		for (int i = 0; i < userRestaurants.size(); i++) {
			String id = userRestaurants.get(i).getId();
			for (int z = 0; z < dislikedRestaurantIds.size(); z++) {
				if (dislikedRestaurantIds.get(z).equals(id)) {
					userRestaurants.remove(i);
					i--;
					z = dislikedRestaurantIds.size();
				}
			}
		}
		
		if (userRestaurants.size() == 0) {
			if (dislikedRestaurantIds.size() == 0) {
				throw new RuntimeException();
			} else {
				throw new IllegalArgumentException();
			}
		}

		Random rand = new Random();
		randomRestaurant = userRestaurants.get(rand.nextInt(userRestaurants.size()));
		return randomRestaurant;
	}

	private Restaurant mapNodeToRestaurant(JsonNode jsonNode) {
		Restaurant aRestaurant = new Restaurant();
		aRestaurant.setId(jsonNode.get("id").asText());
		aRestaurant.setName(jsonNode.get("name").asText());
		aRestaurant.setImageUrl(jsonNode.get("image_url").asText());
		aRestaurant.setUrl(jsonNode.get("url").asText());
		aRestaurant.setDisplayPhone(jsonNode.get("display_phone").asText());

		JsonNode coordinatesNode = jsonNode.get("coordinates");
		aRestaurant.setLatitude(coordinatesNode.get("latitude").asDouble());
		aRestaurant.setLongitude(coordinatesNode.get("longitude").asDouble());

		JsonNode locationNode = jsonNode.get("location");
		Iterator<JsonNode> addressNodes = locationNode.get("display_address").elements();
		String address = "";
		while (addressNodes.hasNext()) {
			address += addressNodes.next().asText() + "\n";
		}
		aRestaurant.setAddress(address);

		if (jsonNode.has("price")) {
			String dollarSigns = jsonNode.get("price").asText(); // api stores the price as series of dollar signs, not
																	// as a number, so we must convert
			aRestaurant.setPrice(dollarSigns.length());
		}

		Iterator<JsonNode> categoryNodes = jsonNode.get("categories").elements();
		while (categoryNodes.hasNext()) {
			aRestaurant.addCategory(categoryNodes.next().get("title").asText());
		}

		// These two lines make the code go slow - we decided to only run it in the
		// controller, when we need it for the next view.
		// I've kept these lines here for historical purposes, but they're not
		// particularly useful
//		List<DailyAvailability> hoursOpen = getHoursOpen(aRestaurant.getId());
//		aRestaurant.setHours(hoursOpen);

		return aRestaurant;
	}

	private String getJsonStringFromUrl(URL url) throws MalformedURLException, IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", API_KEY);

		if (conn.getResponseCode() != 200) {
			System.out.println(url);
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		String jsonString = "";
		while ((output = br.readLine()) != null) {
			jsonString += output;
		}
		conn.disconnect();
		return jsonString;
	}

	private List<Restaurant> getRestaurantsByUserSearch(User aUser) {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		JsonNode jsonNode = null;
		Iterator<JsonNode> jsonArray = null;
		try {
			String queryString = "https://api.yelp.com/v3/businesses/search?limit=50&location=" + aUser.getZipcode()
					+ "&categories=";
			int numOfCategories = 0; //if the user has no categories, this should be 0
			List<String> categories = new ArrayList<String>();
			try {
				categories = aUser.getFoodPreferences();
				numOfCategories = categories.size();
			} catch (NullPointerException e) {
				
			}
			
			if (numOfCategories == 0) { //only search for restaurants if there are no categories
				queryString += "restaurants,all";
			} else {
				for (int i = 0; i < numOfCategories; i++) {
					queryString += categories.get(i);
					if (i + 1 < numOfCategories) {
						queryString += ",";
					}
				}
			}

			try {
				int budget = aUser.getBudget();
				if (budget > 0 && budget < 5) {
					queryString += "&price=" + budget;
				}
			} catch (NullPointerException e) {
				// simply do nothing if the user doesn't have a budget saved
			}
			URL url = new URL(queryString);
			String jsonString = getJsonStringFromUrl(url);
			jsonNode = mapper.readTree(jsonString);

		} catch (MalformedURLException e) {
			System.out.println("What a shitty URL!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Do you even know what a file is??");
			e.printStackTrace();
		}
		jsonArray = jsonNode.get("businesses").elements();
		while (jsonArray.hasNext()) {
			restaurants.add(mapNodeToRestaurant(jsonArray.next()));
		}

		return restaurants;
	}

	private List<Restaurant> filterRestaurantsByPreference(List<Restaurant> restaurants, String preference) {
		List<Restaurant> preferredRestaurants = new ArrayList<Restaurant>();
		for (Restaurant aRestaurant : restaurants) {
			if (aRestaurant.hasCategory(preference)) {
				System.out.println("A hit! We've added the restaurant " + aRestaurant.getName());
				preferredRestaurants.add(aRestaurant);
			} else {
				System.out.println("A miss! We've rejected the restaurant " + aRestaurant.getName());
			}
		}
		return preferredRestaurants;
	}

	private List<Restaurant> filterRedundantRestaurants(List<Restaurant> listOne, List<Restaurant> listTwo) {
		for (Restaurant aRestaurant : listTwo) {
			boolean isMatch = false;
			String idTwo = aRestaurant.getId();
			for (Restaurant newRestaurant : listOne) {
				String idOne = newRestaurant.getId();
				if (idOne.equals(idTwo)) {
					isMatch = true;
				}
			}
			if (!isMatch) {
				listOne.add(aRestaurant);
			}
		}
		return listOne;
	}

	/*
	 * The yelp API only provides hours open if you search by ID. This method does
	 * the search for a restaurant and then returns a list based on the results of
	 * that search
	 */
	public List<DailyAvailability> getHoursOpen(String restaurantId) {
		List<DailyAvailability> hoursOpen = new ArrayList<DailyAvailability>();
		JsonNode hoursNode = null;
		try {
			URL url = new URL("https://api.yelp.com/v3/businesses/" + restaurantId);
			String jsonString = getJsonStringFromUrl(url);
			hoursNode = mapper.readTree(jsonString);
		} catch (MalformedURLException e) {
			System.out.println("What a shitty URL!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Do you even know what a file is?? Moron!!!");
			e.printStackTrace();
		}
		if (hoursNode.has("hours")) {
			Iterator<JsonNode> allHours = hoursNode.get("hours").elements();
			JsonNode singleNode = null;
			singleNode = allHours.next();
			Iterator<JsonNode> hoursNodes = singleNode.get("open").elements();
			while (hoursNodes.hasNext()) {
				DailyAvailability hourForDay = new DailyAvailability();
				JsonNode dayNode = hoursNodes.next();
				hourForDay.setEnd(dayNode.get("end").asInt());
				hourForDay.setStart(dayNode.get("start").asInt());
				hourForDay.setIsOvernight(dayNode.get("is_overnight").asBoolean());
				hourForDay.day = DaysEnum.getDayFromInt(dayNode.get("day").asInt());
				hoursOpen.add(hourForDay);
			}
		}
		return hoursOpen;
	}
	
	public String getMatchingAlias(String foodType) {
		List<Category> allCategories = getAllYelpCategories();
		for (Category category : allCategories) {
			if (category.hasCategory(foodType)) {
				return category.getAlias();
			}
		}
		return "failure";
	}
	
}
