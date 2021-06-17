package com.bridgelabz.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstore.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
