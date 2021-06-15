package com.bridgelabz.bookstore.dto;

import java.io.File;

import javax.persistence.Column;

import lombok.Data;

public @Data class BookDTO {

	private String bookName;
	
	private String author;
	
	private String description;
	
	private File logo;
	
	private double price;
	
	private int quantity;
}
