package com.bridgelabz.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.service.IUserService;
import com.bridgelabz.bookstore.util.Response;

import io.swagger.annotations.ApiOperation;

import com.bridgelabz.bookstore.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	IUserService userService;
	
	/**
	 * Controller to add user
	 * @param dto : user data
	 * @return ResponseEntity<Response>
	 */
	@PostMapping("/newUser")
	public ResponseEntity<Response> addNewUser(@Valid @RequestBody UserDTO dto){
		log.debug("Add new user");
		Response userEntity = userService.addNewUser(dto);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	/**
	 * To get data of all users with token
	 * @param token : To authorize user
	 * @return ResponseEntity<List<?>>
	 */
	@GetMapping("/getUser/{token}")
	public ResponseEntity<List<?>> getAllUsers(@PathVariable String token){
		log.debug("Get all users");
		List<UserEntity> userList = userService.getAllUsers(token);
		return new ResponseEntity<>(userList,HttpStatus.OK);
	}
	
	/**
	 * To verify user using email
	 * @param token : To authorize user
	 * @return ResponseEntity<Response>
	 */
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> verifyUser(@PathVariable String token){
		log.debug("verify user");
		Response userEntity = userService.verifyUser(token);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	/**
	 * To send otp on user's email
	 * @param token : To authorize user
	 * @return ResponseEntity<Response>
	 */
	@GetMapping("/sendOtp/{token}")
	public ResponseEntity<Response> sendOtp(@PathVariable String token){
		log.debug("Send otp");
		Response userEntity = userService.sendOTP(token);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	/**
	 * To verify user using otp
	 * @param token : To authorize user
	 * @return ResponseEntity<Response>
	 */
	@GetMapping("/verifyOtp/{token}")
	public ResponseEntity<Response> verifyOtp(@PathVariable String token,@RequestParam("otp") long otp){
		log.debug("Send otp");
		Response userEntity = userService.verifyOTP(token,otp);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Login Api to take username and password and check if its valid")
	@GetMapping("/login/{token}")
	public ResponseEntity<Response> userLogin(@PathVariable String token,@RequestParam("email") String emailId,
			@RequestParam("password") String password){
		log.debug("User Login.");
		Response userEntity = userService.userLogin(token,emailId,password);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	/**
	 * To update user details
	 * @param token : To authorize user
	 * @param dto : user data
	 * @return ResponseEntity<Response>
	 */
	@PutMapping("/updateUser/{token}")
	public ResponseEntity<Response> updateUser(@PathVariable String token,@RequestBody UserDTO dto){
		log.debug("Update user");
		Response userEntity = userService.updateUser(token,dto);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Forgot password api to set new password")
	@PutMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("emailId") String emailId,
			@RequestParam("newPassword") String newPassword){
		log.debug("Forgot password api");
		Response userEntity = userService.forgotPassword(emailId,newPassword);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	/**
	 * To delete user
	 * @param token : To authorize user
	 * @return ResponseEntity<Response>
	 */
	@DeleteMapping("/deleteUser/{token}")
	public ResponseEntity<Response> deleteUser(@PathVariable String token){
		log.debug("Delete user");
		Response userEntity = userService.deleteUser(token);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
}
