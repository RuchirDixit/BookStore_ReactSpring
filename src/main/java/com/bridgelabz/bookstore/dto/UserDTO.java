package com.bridgelabz.bookstore.dto;

import java.io.File;
import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

public @Data class UserDTO {
	
	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank")
	private String lastName;
	
	@JsonFormat(pattern = "dd MMM yyyy")
	private String dob;
	
	private File kyc;
	
	@NotBlank(message = "Email id cannot be blank")
	private String emailId;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@FutureOrPresent(message = "Start Date should be future or present.")
	private LocalDateTime expiryDate;
}
