package com.bridgelabz.bookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bookstore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// to find orders by user id
	Optional<Order> findByUserId(long userId);
	
	@Query(value = "SELECT * FROM orders WHERE user_id=:userId",nativeQuery = true)
	List<Order> getAllOrdersForUser(long userId);
}
