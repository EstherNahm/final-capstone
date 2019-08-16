package com.techelevator.model.restaurant;

public class DailyAvailability {
	
	private boolean isOvernight;
	private int start;
	private int end;
	public DaysEnum day;
	
	public DaysEnum getDay() {
		return day;
	}
	public void setDay(DaysEnum day) {
		this.day = day;
	}
	public void setOvernight(boolean isOvernight) {
		this.isOvernight = isOvernight;
	}
	public boolean getIsOvernight() {
		return isOvernight;
	}
	public void setIsOvernight(boolean isOvernight) {
		this.isOvernight = isOvernight;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	
}
