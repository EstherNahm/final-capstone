package com.techelevator.model.restaurant;

public enum DaysEnum {
	Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday;
	
	public String toString() {
		String result = "";
		switch(this) {
		case Monday:
			result = "Mon";
			break;
		case Tuesday:
			result = "Tues";
			break;
		case Wednesday:
			result = "Wed";
			break;
		case Thursday:
			result = "Thur";
			break;
		case Friday:
			result = "Fri";
			break;
		case Saturday:
			result = "Sat";
			break;
		case Sunday:
			result = "Sun";
			break;
		}
		return result;
	}
	
	public static DaysEnum getDayFromInt(int day) {
		switch(day) {
		case 0:
			return DaysEnum.Monday;
		case 1:
			return DaysEnum.Tuesday;
		case 2:
			return DaysEnum.Wednesday;
		case 3:
			return DaysEnum.Thursday;
		case 4:
			return DaysEnum.Friday;
		case 5:
			return DaysEnum.Saturday;
		case 6:
			return DaysEnum.Sunday;
		default:
			return DaysEnum.Monday;
		}
	}
}
