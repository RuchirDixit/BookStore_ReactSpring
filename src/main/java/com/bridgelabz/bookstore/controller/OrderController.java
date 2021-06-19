package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.service.IOrderService;
import com.bridgelabz.bookstore.util.Response;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
	IOrderService orderService;
	
	@ApiOperation(value = "Api to place an order for user with specified book Id in DTO")
	@PostMapping("placeOrder/{token}")
	public ResponseEntity<Response> placeOrder(@PathVariable String token,@RequestBody OrderDTO orderDTO){
		log.debug("Place order");
		Response response = orderService.placeOrder(token,orderDTO);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@ApiOperation(value = "To cancel order with mentioned order id")
	@DeleteMapping("/cancelOrder/{token}")
	public ResponseEntity<Response> cancelOrder(@PathVariable String token,@RequestParam("orderId") long orderId){
		log.debug("Place order");
		Response response = orderService.cancelOrder(token,orderId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Api to get all the orders present")
	@GetMapping("/getAllOrder/{token}")
	public ResponseEntity<List<?>> getAllOrders(@PathVariable String token){
		log.debug("Get all orders");
		List<Order> orders = orderService.getAllOrders(token);
		return new ResponseEntity<List<?>>(orders,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Api to get all the orders placed by user in token")
	@GetMapping("/getUserOrders/{token}")
	public ResponseEntity<List<?>> getUserOrders(@PathVariable String token){
		log.debug("Get all orders for user");
		List<Order> orders = orderService.getUserOrders(token);
		return new ResponseEntity<List<?>>(orders,HttpStatus.OK);
	}
	
}
