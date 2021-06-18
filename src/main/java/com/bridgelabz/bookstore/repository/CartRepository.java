package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstore.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

	Optional<CartEntity> findByBookId(long bookId);
}
