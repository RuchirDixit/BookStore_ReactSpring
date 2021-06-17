package com.bridgelabz.bookstore.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cart")
public @Data class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	@ElementCollection
	private List<UserEntity> user;
	
	@Column
	@ElementCollection
	private List<BookEntity> books;
	
	private int quantity;
	
}
