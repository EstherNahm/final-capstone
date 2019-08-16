package com.techelevator.model.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

	private String id;
	private String name;
	private String imageUrl;
	private String url;
	private String displayPhone;
	private List<String> categories;
	private String address;
	//private List<String> photos;
	private int price;
	private List<DailyAvailability> hours;
	private List<String> days;
	private double latitude;
	private double longitude;
	public Restaurant() {
		days = new ArrayList<String>();
		categories = new ArrayList<String>();
		//photos = new ArrayList<String>();
		hours = new ArrayList<DailyAvailability>();
	}
	public List<String> getDays() {
		return days;
	}
	public void setDays(List<String> days) {
		this.days = days;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDisplayPhone() {
		return displayPhone;
	}
	public void setDisplayPhone(String displayPhone) {
		this.displayPhone = displayPhone;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public void addCategory(String category) {
		this.categories.add(category);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	//	public List<String> getPhotos() {
//		return photos;
//	}
//	public void setPhotos(List<String> photos) {
//		this.photos = photos;
//	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<DailyAvailability> getHours() {
		return hours;
	}
	public void setHours(List<DailyAvailability> hours) {
		this.hours = hours;
	}
	
	public List<String> getOpenCloseTimes() {
		List<String> openCloseTimes = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			DaysEnum today = DaysEnum.getDayFromInt(i);
			String daysHours = today + ": ";
			boolean matchingDay = false;
			for (DailyAvailability aDay : hours) {
				if (today == aDay.day) {
					if (matchingDay) {
						daysHours += "; ";
					}
					matchingDay = true;
					String openTime = convertIntToTimeDisplay(aDay.getStart(),true);
					String closeTime = convertIntToTimeDisplay(aDay.getEnd(),false);
					daysHours += openTime + " - " + closeTime;
				}
			}
			if (!matchingDay) {
				daysHours += " Closed";
			}
			openCloseTimes.add(daysHours);
		}
		return openCloseTimes;
	}
	
	private String convertIntToTimeDisplay(int time, boolean open) {
		String timeDisplay = "";
		String amOrPm = " am";
		if (time >= 1300) {
			time -= 1200;
			amOrPm = " pm";
		}
		timeDisplay = ((Integer)time).toString();
		if (timeDisplay.length() > 2) {
			timeDisplay = timeDisplay.substring(0,timeDisplay.length()-2) + ":" 
		+ timeDisplay.substring(timeDisplay.length()-2);
		} else {
			if (timeDisplay.length() == 2) {
				timeDisplay = "12:" + timeDisplay;
			} else {
				timeDisplay = "12:0" + timeDisplay;
			}
		}
		return timeDisplay + amOrPm;
	}
	
	public boolean hasCategory(String searchFor) {
		searchFor = searchFor.toLowerCase();
		for (String category : categories) {
			if (category.toLowerCase().equals(searchFor)) {
				return true;
			}
		}
		return false;
	}
}
