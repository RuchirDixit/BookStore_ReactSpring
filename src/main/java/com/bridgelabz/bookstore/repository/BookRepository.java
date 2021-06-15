package com.bridgelabz.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstore.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
