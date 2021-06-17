package com.bridgelabz.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.entity.BookEntity;
import com.bridgelabz.bookstore.entity.CartEntity;
import com.bridgelabz.bookstore.entity.UserEntity;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService implements ICartService {

	@Autowired 
	CartRepository cartRepository;
	
	@Autowired
	UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	TokenUtil tokenUtil;
	
	@Override
	public Response addToCart(String token, long bookId, int quantity) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			Optional<BookEntity> isBookPresent = bookRepository.findById(bookId);
			if(isBookPresent.isPresent()) {
				CartEntity cartEntity = new CartEntity();
				cartEntity.getUser().add(isUserPresent.get());
				cartEntity.getBooks().add(isBookPresent.get());
				cartEntity.setQuantity(quantity);
				cartRepository.save(cartEntity);
				return new Response(200, "Book with id:" + bookId + " Added to cart.", null);
				
			} else {
				log.error("Book not found");
				throw new BookStoreException(404, "Book not found");
			}
		}
		else {
			log.error("User not found");
			throw new BookStoreException(404, "User not found");
		}
	}

}
