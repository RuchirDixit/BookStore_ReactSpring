package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.service.ICartService;
import com.bridgelabz.bookstore.util.Response;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cart")
@Slf4j
public class CartController {

	@Autowired
	ICartService cartService;
	
	@ApiOperation(value = "To add item to cart on with Book id and quantity from user")
	@PostMapping("/addToCart/{token}")
	public ResponseEntity<Response> addBookToCart(@PathVariable String token,@RequestBody CartDTO cartDto) {
		log.debug("Add prod to cart");
		Response response = cartService.addToCart(token,cartDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "To remove item with mentioned id from cart")
	@DeleteMapping("/removeCartItem/{token}")
	public ResponseEntity<Response> removeCartItem(@PathVariable String token,@RequestParam("cartId") long cartId) {
		log.debug("Remove prod from cart");
		Response response = cartService.removeCartItem(token,cartId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update quantity of item in cart")
	@PutMapping("/updateCartItem/{token}")
	public ResponseEntity<Response> updateCartItem(@PathVariable String token,@RequestBody CartDTO cartDto) {
		log.debug("Update cart");
		Response response = cartService.updateCartItem(token,cartDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
