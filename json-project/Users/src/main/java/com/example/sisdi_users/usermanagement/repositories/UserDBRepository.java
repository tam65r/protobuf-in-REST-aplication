package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.usermanagement.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDBRepository extends CrudRepository<User, Long> {
	Optional<User> findById(Long id);

	@Cacheable
	@Query("SELECT u FROM User u WHERE u.username = :username")
	Optional<User> findByUsername(String username);
}
