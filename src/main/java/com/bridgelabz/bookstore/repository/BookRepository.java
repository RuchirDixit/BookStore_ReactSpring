package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstore.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

	// To search if book with name exists
	Optional<BookEntity> findByBookName(String bookName);
}
