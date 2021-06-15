package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.service.IBookService;
import com.bridgelabz.bookstore.util.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

	@Autowired
	IBookService bookService;
	
	/**
	 * Add new book to store
	 * @param bookDto
	 * @return
	 */
	@PostMapping("/addBook")
	public ResponseEntity<Response> addBook(@RequestBody BookDTO bookDto){
		log.debug("Add new book");
		Response response = bookService.addNewBook(bookDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
