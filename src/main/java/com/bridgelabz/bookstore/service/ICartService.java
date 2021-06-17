package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.util.Response;

public interface ICartService {

	// to add book to cart
	Response addToCart(String token, long bookId, int quantity);

}
