package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

public @Data class OrderDTO {

	@PositiveOrZero
	private int quantity;
	
	private double price;
	
	@NotBlank(message = "Address cannot be left blank.")
	private String address;
	
	private long bookId;
}
