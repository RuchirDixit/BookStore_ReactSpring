package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orders")
public @Data class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "orderDate")
	private LocalDateTime orderDate = LocalDateTime.now();
	
	private int quantity;
	
	private double price;
	
	private String address;
	
	@Column(name = "user_id")
	private long userId;
	
	private long bookId;
}
