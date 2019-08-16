package com.techelevator.controller.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6974644855556556894L;

	private String itemId;

	public RestaurantNotFoundException(String id, String message) {
		super(message);
		setItemId(id);
	}


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String id) {
		this.itemId = id;
	}

}
