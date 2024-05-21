package com.example.sisdi_plans.planmanagement.repositories;

import com.example.sisdi_plans.planmanagement.model.PlanJPA;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanDBRepository extends CrudRepository<PlanJPA, Long> {
	@Query("SELECT p FROM PlanJPA p WHERE p.name = :name")
	Optional<PlanJPA> findByName(String name);

	@Query("SELECT p FROM PlanJPA p WHERE p.id = :id")
	Optional<PlanJPA> findByName(long id);

	@Query("SELECT p.name FROM PlanJPA p WHERE p.id = :id")
	Optional<String> findNameById(long id);

	@Query("SELECT p FROM PlanJPA p WHERE p.isActive = true ")
	Iterable<PlanJPA> findAllByStatus();

}
