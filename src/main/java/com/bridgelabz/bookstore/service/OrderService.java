package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.entity.BookEntity;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.entity.UserEntity;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService implements IOrderService {
	
	@Autowired
	UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TokenUtil tokenUtil;
	
	/**
	 * To place order mentioned by Id
	 * @param token : JWT to verify user
	 * @param orderDTO : DTO with order details
	 * @return Response
	 */
	@Override
	public Response placeOrder(String token, OrderDTO orderDTO) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			Optional<BookEntity> isBookPresent = bookRepository.findById(orderDTO.getBookId());
			if(isBookPresent.isPresent()) {
				Order order = modelMapper.map(orderDTO, Order.class);
				order.setUserId(id);
				order.setBookId(orderDTO.getBookId());
				order.setAddress(orderDTO.getAddress());
				order.setPrice(orderDTO.getPrice());
				order.setQuantity(orderDTO.getQuantity());
				orderRepository.save(order);
				// To reduce quantity of books in store
				isBookPresent.get().setQuantity(isBookPresent.get().getQuantity() - orderDTO.getQuantity());
				bookRepository.save(isBookPresent.get());
				log.debug("Order placed successfully.");
				try {
					emailService.sendmail(isUserPresent.get().getEmailId(),"Order placed","Your order has been placed successfully. Order id is:" + order.getId());
				} catch (MessagingException | IOException e) {
					log.error("Email sending error.");
					e.printStackTrace();
				}
				return new Response(200, "Order placed successfully! Thank you.", null);
			}
			else {
				log.error("Book not found");
				throw new BookStoreException(404, "Book not found.");
			}
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404, "User not found.");
		}
	}

	/**
	 * To cancel order
	 * @param token : JWT to verify user
	 * @param orderId : Order to cancel
	 * @return Response
	 */
	@Override
	public Response cancelOrder(String token, long orderId) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			Optional<Order> doesOrderExists = orderRepository.findById(orderId);
			if(doesOrderExists.isPresent()) {
				Optional<BookEntity> isBookPresent = bookRepository.findById(doesOrderExists.get().getBookId());
				if(isBookPresent.isPresent()) {
					// To add quantity in bookstore after canceling the order
					isBookPresent.get().setQuantity(isBookPresent.get().getQuantity() + doesOrderExists.get().getQuantity());
					bookRepository.save(isBookPresent.get());
					orderRepository.deleteById(orderId);
					log.debug("Order canceled.");
					try {
						emailService.sendmail(isUserPresent.get().getEmailId(),"Order cancellation","Your order has been canceled.");
					} catch (MessagingException | IOException e) {
						log.error("Email sending error.");
						e.printStackTrace();
					}
					return new Response(200, "Order canceled", null);
				}
				else {
					log.error("Book not found");
					throw new BookStoreException(404, "Book not found.");
				}
			}
			else {
				log.error("No such order.");
				throw new BookStoreException(400, "No such order has been placed.");
			}
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404, "User not found.");
		}
	}

	/**
	 * To get all orders
	 * @param token : JWT to verify user
	 * @return : List<Order>
	 */
	@Override
	public List<Order> getAllOrders(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			List<Order> orders = orderRepository.findAll();
			return orders;
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404, "User not found.");
		}
	}

	/**
	 * To get all orders for user
	 * @param token : JWT to verify user
	 * @return : List<Order>
	 */
	@Override
	public List<Order> getUserOrders(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			Optional<Order> orderForUserExists = orderRepository.findByUserId(id);
			if(orderForUserExists.isPresent()) {
				List<Order> usersOrders = orderRepository.getAllOrdersForUser(id);
				return usersOrders;
			}
			else {
				log.error("No orders exists for user");
				throw new BookStoreException(404, "No orders exists for user");
			}
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404, "User not found.");
		}
	}

}
