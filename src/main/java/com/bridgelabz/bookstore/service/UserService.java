package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.entity.UserEntity;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import com.bridgelabz.bookstore.service.EmailService;
import com.bridgelabz.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService {
	
	@Autowired
	public UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	TokenUtil tokenUtil;
	
	@Autowired
	EmailService emailService;
	
	/**
	 * To add new user to database
	 * @param dto: User DTO
	 * @return : Response
	 */
	@Override
	public Response addNewUser(@Valid UserDTO dto) {
		String host = System.getenv("HOST_NAME");
		String port = System.getenv("HOST_PORT");
		Optional<UserEntity> isPresent = userRegistrationRepository.findByEmailId(dto.getEmailId());
		if(isPresent.isPresent()) {
			log.error("User exists already. Email:" + dto.getEmailId());
			throw new BookStoreException(400,"User already exists. Email:"+dto.getEmailId());
		}else {
			UserEntity userEntity = modelMapper.map(dto,UserEntity.class);
			userEntity.setPurchaseDate(LocalDateTime.now());
			userEntity.setExpiryDate(dto.getExpiryDate());
			userRegistrationRepository.save(userEntity);
			String token = tokenUtil.createToken(userEntity.getId());
			try {
				// Send email to user for verification
				emailService.sendmail(dto.getEmailId(),"User Verification","Before logging in, Please click on the given link to verify : \n http://"+host+":"+port+"/user/verify/"+token);
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
			}
			log.debug("User added.");
			return new Response(200, "User registered successfully!", token);
		}
	}

	/**
	 * To verify use
	 * @param token : Jwt to check userid
	 * @return : Response
	 */
	@Override
	public Response verifyUser(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setVerify(true);
			userRegistrationRepository.save(isUserPresent.get());
			log.debug("User: " + isUserPresent.get() + " Verified!");
			return new Response(200, "User verified successfully!", null);
		}
		else {
			log.error("User not found.");
			throw new BookStoreException(404,"User Not found");
		}
	}

	/**
	 * To get all users
	 * @param token : Jwt to check userid
	 * @return  List<UserEntity>
	 */
	@Override
	public List<UserEntity> getAllUsers(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isContactPresent = userRegistrationRepository.findById(id);
		if(isContactPresent.isPresent()) {
			log.debug("Get all users");
			List<UserEntity> getUsers=userRegistrationRepository.findAll();
			return getUsers;
		}
		else {
			log.error("Token not valid");
			throw new BookStoreException(400,"Token not valid");
		}
	}

	/**
	 * To update user details
	 * @param token : Jwt to check userid, dto : UserDTO
	 * @return Response
	 */
	@Override
	public Response updateUser(String token, UserDTO dto) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setFirstName(dto.getFirstName());
			isUserPresent.get().setLastName(dto.getLastName());
			isUserPresent.get().setDob(dto.getDob());
			isUserPresent.get().setKyc(dto.getKyc());
			isUserPresent.get().setEmailId(dto.getEmailId());
			isUserPresent.get().setPassword(dto.getPassword());
			isUserPresent.get().setUpdatedDate(LocalDateTime.now());
			isUserPresent.get().setExpiryDate(dto.getExpiryDate());
			userRegistrationRepository.save(isUserPresent.get());
			log.debug("User updated" + isUserPresent.get());
			return new Response(200, "User updated successfully", null);
		}
		else {
			log.error("User not found.");
			throw new BookStoreException(404,"user Not found");
		}
	}

	/**
	 * To delete user
	 * @param token : JWT to get userid
	 * @return Response
	 */
	@Override
	public Response deleteUser(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			userRegistrationRepository.delete(isUserPresent.get());
			log.debug("User deleted!!");
			return new Response(200, "User deleted successfully", null);
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404,"User not found");
		}
	}

	/**
	 * To send otp to user
	 * @param token : JWT to get userid
	 * @return Response
	 */
	@Override
	public Response sendOTP(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			  Random rnd = new Random();
			  int number = rnd.nextInt(999999);
			  isUserPresent.get().setOtp(number);
			  userRegistrationRepository.save(isUserPresent.get());
			  try {
					// Send email to user with OTP
					emailService.sendmail(isUserPresent.get().getEmailId(),"OTP","Your Otp for bookstore application is :" + number);
				} catch (MessagingException | IOException e) {
					e.printStackTrace();
				}
			  log.debug("OTP sent!!");
			return new Response(200, "OTP sent.", null);
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404,"User not found");
		}
	}

	/**
	 * To verify user otp
	 * @param token : JWT to get userid, int : OTP
	 * @return Response
	 */
	@Override
	public Response verifyOTP(String token,long otp) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			long getOtp = isUserPresent.get().getOtp();
			if(getOtp == otp) {
				log.debug("OTP verified!!");
				return new Response(200, "OTP verified.", null);
			}
			else {
				log.error("Incorrect otp.");
				return new Response(400, "Incorrect Otp. Please enter correct otp.", null);
			}
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404,"User not found");
		}
	}

	/**
	 * To login user using email id and password if verified
	 * @param token : JWT to get userid
	 * @param emailId : user's email id
	 * @param password : user's password
	 * @return Response
	 */
	@Override
	public Response userLogin(String token, String emailId, String password) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			String userEmail = isUserPresent.get().getEmailId();
			String userPassword = isUserPresent.get().getPassword();
			if(userEmail.equals(emailId) && userPassword.equals(userPassword) && isUserPresent.get().isVerify() == true) {
				log.debug("User logged in.");
				return new Response(200, "User logged in successfully.", token);
			}
			else {
				log.error("Incorrect credentials");
				return new Response(403, "Incorrect credentials. Enter valid details.", null);
			}
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404,"User not found");
		}
	}

	@Override
	public Response forgotPassword(String emailId, String newPassword) {
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findByEmailId(emailId);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setPassword(newPassword);
			userRegistrationRepository.save(isUserPresent.get());
			log.debug("Password reset.");
			try {
				emailService.sendmail(emailId,"Password reset","Your password has been reset. If it was not you please contact admin immediately.");
			} catch (MessagingException | IOException e) {
				log.error("Mail sending error.");
				e.printStackTrace();
			}
			return new Response(200, "Password reset successful.", null);
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404,"User not found");
		}
	}

}
