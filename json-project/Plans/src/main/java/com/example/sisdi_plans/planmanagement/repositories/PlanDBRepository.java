package com.example.sisdi_plans.planmanagement.repositories;

import com.example.sisdi_plans.planmanagement.model.Plan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanDBRepository extends CrudRepository<Plan, Long> {
	@Query("SELECT p FROM Plan p WHERE p.name = :name")
	Optional<Plan> findByName(String name);

	@Query("SELECT p FROM Plan p WHERE p.id = :id")
	Optional<Plan> findByName(long id);

	@Query("SELECT p.name FROM Plan p WHERE p.id = :id")
	Optional<String> findNameById(long id);

	@Query("SELECT p FROM Plan p WHERE p.isActive = true ")
	Iterable<Plan> findAllByStatus();

}
