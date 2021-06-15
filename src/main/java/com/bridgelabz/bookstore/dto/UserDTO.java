package com.bridgelabz.bookstore.dto;

import java.io.File;
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
}
