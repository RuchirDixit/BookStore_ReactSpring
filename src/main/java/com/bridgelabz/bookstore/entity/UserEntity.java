package com.bridgelabz.bookstore.entity;

import java.io.File;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user")
public @Data class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long Id;
	
	@Column(name = "fname")
	private String firstName;
	
	@Column(name = "lname")
	private String lastName;
	
	private File kyc;
	
	@Column(name = "dob")
	private String dob;

	@Column(name = "registerDate")
	private LocalDateTime registerDate = LocalDateTime.now();

	@Column(name = "updateddate")
	private LocalDateTime updatedDate;
	
	@Column(name = "emailid")
	private String emailId;

	@Column(name = "password")
	private String password;
	
	@Column(name = "verification")
	private boolean verify = false;
	
	private long otp;
	
	private LocalDateTime purchaseDate;
	
	private LocalDateTime expiryDate;
	
}
