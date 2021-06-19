package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.util.Response;

public interface IOrderService {

	// To place an order
	Response placeOrder(String token, OrderDTO orderDTO);

	// To cancel placed order
	Response cancelOrder(String token, long orderId);

	// To get all orders placed
	List<Order> getAllOrders(String token);

	// To get all order placed by user
	List<Order> getUserOrders(String token);

}
