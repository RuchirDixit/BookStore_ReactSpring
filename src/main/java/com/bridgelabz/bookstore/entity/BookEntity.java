package com.bridgelabz.bookstore.entity;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "books")
public @Data class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;
	
	@Column(name = "bookName")
	private String bookName;
	
	@Column(name = "bookAuthor")
	private String author;
	
	@Column(name = "bookDescription")
	private String description;
	
	@Column(name = "bookLogo")
	private String logo;
	
	@Column(name = "bookPrice")
	private double price;
	
	@Column(name = "bookQuantity")
	private int quantity;
	
	
}
