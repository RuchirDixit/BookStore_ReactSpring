package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.entity.BookEntity;
import com.bridgelabz.bookstore.util.Response;

public interface IBookService {

	// To add new book to store
	Response addNewBook(String token, BookDTO bookDto);

	// Get all books
	List<BookEntity> getAllBooks(String token);

	// To update book details
	Response updateBook(String token, BookDTO bookDto);

	// To delete user
	Response deleteBook(String token, String bookName);

	// To update book quantity
	Response updateBookQuantity(String token, String bookName,int newQuantity);

	// To update book price
	Response updateBookPrice(String token, String bookName, Double price);

	// Get Book by Id
	BookEntity getBookById(String token, long bookId);

}
