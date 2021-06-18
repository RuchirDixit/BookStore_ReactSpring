package com.bridgelabz.bookstore.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.entity.UserEntity;
import com.bridgelabz.bookstore.util.Response;

public interface IUserService {

		// To add new user
		Response addNewUser(@Valid UserDTO dto);

		// To verify user
		Response verifyUser(String token);
		
		// To get list of all users
		List<UserEntity> getAllUsers(String token);

		// To update user data
		Response updateUser(String token, UserDTO dto);

		// To delete user
		Response deleteUser(String token);

		// To send otp to user
		Response sendOTP(String token);

		// Verify use using OTP
		Response verifyOTP(String token, long otp);

		// User login with verification
		Response userLogin(String token, String emailId, String password);

		// To reset password
		Response forgotPassword(String emailId, String newPassword);
}
