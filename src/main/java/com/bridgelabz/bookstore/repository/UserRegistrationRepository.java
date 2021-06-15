package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstore.entity.UserEntity;

public interface UserRegistrationRepository extends JpaRepository<UserEntity,Long> {

	Optional<UserEntity> findByEmailId(String emailId);
}
