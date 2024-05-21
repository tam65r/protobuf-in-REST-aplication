package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.usermanagement.model.UserJPA;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDBRepository extends CrudRepository<UserJPA, Long> {
	Optional<UserJPA> findById(Long id);

	@Cacheable
	@Query("SELECT u FROM UserJPA u WHERE u.username = :username")
	Optional<UserJPA> findByUsername(String username);
}
