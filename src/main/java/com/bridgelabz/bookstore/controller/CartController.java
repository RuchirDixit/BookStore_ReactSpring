package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.service.ICartService;
import com.bridgelabz.bookstore.util.Response;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cart")
@Slf4j
public class CartController {

	@Autowired
	ICartService cartService;
	
	@ApiOperation(value = "To add item to cart on with Book id and quantity from user")
	@PostMapping("addToCart/{token}")
	public ResponseEntity<Response> addBookToCart(@PathVariable String token,@RequestParam("bookid") long bookId,
			@RequestParam("quantity") int quantity) {
		log.debug("Add prod to cart");
		Response response = cartService.addToCart(token,bookId,quantity);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
