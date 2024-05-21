package com.example.sisdi_plans.planmanagement.api;

import com.example.sisdi_plans.planmanagement.model.AuthorityRole;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
import com.example.sisdi_plans.planmanagement.model.proto.PlanEntity.Plan;
import com.example.sisdi_plans.planmanagement.model.proto.PlanEntity.PlanList;

import com.example.sisdi_plans.planmanagement.api.proto.PlanRequests.CreatePlanRequest;
import com.example.sisdi_plans.planmanagement.api.proto.PlanRequests.EditPlanRequest;

import com.example.sisdi_plans.planmanagement.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Tag(name = "Plans")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
class PlanController {
	private final PlanService service;

	private final PlanDTOMapper mapper;

	@Operation(summary = "Gets all plans")
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public PlanList getAll() throws Exception {
		Iterable<PlanJPA> jpa = service.getAll(false);
		List<PlanJPA> jpaList = StreamSupport.stream(jpa.spliterator(), false)
				.collect(Collectors.toList());
		return mapper.toDTOPlanList(mapper.toDTOEntityList(jpaList));
	}

	@Operation(summary = "Internal endpoint to get all plans")
	@GetMapping("/internal/all")
	@ResponseStatus(HttpStatus.OK)
	public PlanList getAllInternal() throws Exception{
		Iterable<PlanJPA> jpa = service.getAll(true);
		List<PlanJPA> jpaList = StreamSupport.stream(jpa.spliterator(), false)
				.collect(Collectors.toList());
		return mapper.toDTOPlanList(mapper.toDTOEntityList(jpaList));
	}

	@Operation(summary = "Deactivates a specific plan")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@PatchMapping(value = "/deactivate/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Plan> deactivatePlan(@PathVariable("name") String name, HttpServletRequest request) throws Exception {

		String authorization = request.getHeader("Authorization");

		final var updatedPlan = service.changeActivityStatus(name, authorization, false);

		return ResponseEntity.ok().body(mapper.toDTOEntity(updatedPlan));
	}

	@Operation(summary = "Deactivates a specific plan")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@PatchMapping(value = "/internal/deactivate/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Plan> deactivatePlanInternal(@PathVariable("name") String name) throws Exception{
		final var updatedPlan = service.changeActivityStatus(name, null, true);

		return ResponseEntity.ok().body(mapper.toDTOEntity(updatedPlan));
	}


	@Operation(summary = "Create a new plan")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Plan> create(@Valid @RequestBody final CreatePlanRequest resource) throws Exception {

		final var plan = service.create(resource);

		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(plan.getName())
				.build()
				.toUri();
		return ResponseEntity.created(uri).body(mapper.toDTOEntity(plan));
	}

	@Operation(summary = "Edit a specific plan")
	@PatchMapping ("/{name}")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Plan> editPlan(
			@PathVariable("name") String name,
			@Valid @RequestBody EditPlanRequest resource, HttpServletRequest request) throws Exception {

		String authorization = request.getHeader("Authorization");

		final var editPlan = service.editPlan(name, resource, authorization, false);

		return ResponseEntity.ok().body(mapper.toDTOEntity(editPlan));
	}

	@Operation(summary = "Edit a specific plan")
	@PatchMapping ("/internal/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Plan> editInternalPlan(
			@PathVariable("name") String name,
			@Valid @RequestBody EditPlanRequest request) throws Exception {


			final var editPlan = service.editPlan(name, request, null, true);

		return ResponseEntity.ok().body(mapper.toDTOEntity(editPlan));
	}

	@Operation(summary = "Get a specific Plan")
	@GetMapping("/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Plan> findSpecificPlan(
			@PathVariable("name") String name) throws Exception {
		PlanJPA planJPA = service.findByName(name,false);
		return ResponseEntity.ok().body(mapper.toDTOEntity(planJPA));

	}

	@Operation(summary = "Internal endpoint to get a specific plan")
	@GetMapping("/internal/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Plan> findSpecificPlanInternal(
			@PathVariable("name") String name) throws Exception {
		PlanJPA planJPA = service.findByName(name,true);
		return ResponseEntity.ok().body(mapper.toDTOEntity(planJPA));
	}
}
