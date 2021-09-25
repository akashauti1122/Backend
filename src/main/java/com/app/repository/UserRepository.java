package com.app.repository;

import java.util.Optional;

import com.app.entity.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//authenticate user
	Optional<User> findByEmail(String email);

}
