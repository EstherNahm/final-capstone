package com.techelevator.model.restaurant;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores a single yelp category
 */
public class Category {
	private String alias;
	private String title;
	private List<String> parents;
	public Category() {
		parents = new ArrayList<String>();
	}
	public List<String> getParents() {
		return parents;
	}
	public void setParents(List<String> parents) {
		this.parents = parents;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean hasCategory(String foodType) {
		foodType = foodType.toLowerCase();
		String lowerCaseAlias = alias.toLowerCase();
		String lowerCaseTitle = title.toLowerCase();
		if (lowerCaseAlias.contains(foodType)||foodType.contains(lowerCaseAlias)
				||lowerCaseTitle.contains(foodType)||foodType.contains(lowerCaseTitle)) {
			return true;
		}
		
//		for (String parent : parents) {
//			String lowerCaseParent = parent.toLowerCase();
//			if (lowerCaseParent.contains(foodType)||foodType.contains(lowerCaseParent)) {
//				return true;
//			}
//		}
		
		return false;
	}
}
