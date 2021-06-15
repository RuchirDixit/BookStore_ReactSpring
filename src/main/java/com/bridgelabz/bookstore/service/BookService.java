package com.bridgelabz.bookstore.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.entity.BookEntity;
import com.bridgelabz.bookstore.entity.UserEntity;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserRegistrationRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	TokenUtil tokenUtil;
	
	/**
	 * Add new book
	 * @param token : JWT to verify user, bookDto : Book details
	 * @return Response
	 */
	@Override
	public Response addNewBook(String token,BookDTO bookDto) {
		// check if user is present
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRepository.findById(id);
		if(isUserPresent.isPresent()) {
			// check if book is already present
			Optional<BookEntity> isBookPresent = bookRepository.findByBookName(bookDto.getBookName());
			if(isBookPresent.isPresent()) {
				log.error("Book exists already");
				throw new BookStoreException(400,"Book already exists.");
			}
			else {
				BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);
				bookRepository.save(bookEntity);
				log.debug("New book added.");
				return new Response(200, "New Book Added.", null);
			}
		}
		else {
			log.error("User not found.");
			throw new BookStoreException(404,"User Not found");
		}
	}

	/**
	 * Fetch all books
	 * @param token : JWT to verify user
	 * @return List<BookEntity>
	 */
	@Override
	public List<BookEntity> getAllBooks(String token) {
		// check if user is present
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRepository.findById(id);
		if(isUserPresent.isPresent()) {
			List<BookEntity> books = bookRepository.findAll();
			return books;
		}
		else {
			log.error("User not found.");
			throw new BookStoreException(404,"User Not found");
		}
	}

	/**
	 * To update book data
	 * @param token : JWT to verify user, bookDto : Book details
	 * @return Response
	 */
	@Override
	public Response updateBook(String token, BookDTO bookDto) {
		// check if user is present
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRepository.findById(id);
		if(isUserPresent.isPresent()) {
			// check if book present
			Optional<BookEntity> isBookPresent = bookRepository.findByBookName(bookDto.getBookName());
			if(isBookPresent.isPresent()) {
				isBookPresent.get().setAuthor(bookDto.getAuthor());
				isBookPresent.get().setDescription(bookDto.getDescription());
				isBookPresent.get().setLogo(bookDto.getLogo());
				isBookPresent.get().setPrice(bookDto.getPrice());
				isBookPresent.get().setQuantity(bookDto.getQuantity());
				bookRepository.save(isBookPresent.get());
				log.debug("Book updated.");
				return new Response(200, "Book Updated.", null);
			}
			else {
				log.error("Book not found.");
				throw new BookStoreException(404,"Book Not found");
			}
		}
		else {
			log.error("User not found.");
			throw new BookStoreException(404,"User Not found");
		}
	}

	/**
	 * To delete book data
	 * @param token : JWT to verify user, bookName : Name of book to delete
	 * @return Response
	 */
	@Override
	public Response deleteBook(String token,String bookName) {
		// check if user is present
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRepository.findById(id);
		if(isUserPresent.isPresent()) {
			// check if book present
			Optional<BookEntity> isBookPresent = bookRepository.findByBookName(bookName);
			if(isBookPresent.isPresent()) {
				bookRepository.delete(isBookPresent.get());
				log.debug("Book deleted.");
				return new Response(200, "Book Deleted.", null);
			}
			else {
				log.error("Book not found.");
				throw new BookStoreException(404,"Book Not found");
			}
		}
		else {
			log.error("User not found.");
			throw new BookStoreException(404,"User Not found");
		}
	}

}
