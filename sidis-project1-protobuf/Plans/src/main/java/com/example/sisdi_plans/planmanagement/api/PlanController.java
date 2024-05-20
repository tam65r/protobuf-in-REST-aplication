package com.example.sisdi_plans.planmanagement.api;

import com.example.sisdi_plans.planmanagement.model.AuthorityRole;
import com.example.sisdi_plans.planmanagement.model.Plan;
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

@Tag(name = "Plans")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
class PlanController {
	private final PlanService service;

	private final PlanDTOMapper planMapper;

	@Operation(summary = "Gets all plans")
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<PlanDTO> getAll() throws Exception {
		return planMapper.toPlanView(service.getAll(false));
	}

	@Operation(summary = "Internal endpoint to get all plans")
	@GetMapping("/internal/all")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<PlanDTO> getAllInternal() throws Exception{
		return planMapper.toPlanView(service.getAll(true));
	}

	@Operation(summary = "Deactivates a specific plan")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@PatchMapping(value = "/deactivate/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PlanDTO> deactivatePlan(@PathVariable("name") String name, HttpServletRequest request) throws Exception {

		String authorization = request.getHeader("Authorization");

		final var updatedPlan = service.changeActivityStatus(name, authorization, false);

		return ResponseEntity.ok().body(planMapper.toPlanView(updatedPlan));
	}

	@Operation(summary = "Deactivates a specific plan")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@PatchMapping(value = "/internal/deactivate/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PlanDTO> deactivatePlanInternal(@PathVariable("name") String name) throws Exception{
		final var updatedPlan = service.changeActivityStatus(name, null, true);

		return ResponseEntity.ok().body(planMapper.toPlanView(updatedPlan));
	}

	@Operation(summary = "Create a new plan")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PlanDTO> create(@Valid @RequestBody final CreatePlanRequest resource) throws Exception {

		final var plan = service.create(resource);

		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(plan.getName())
				.build()
				.toUri();
		return ResponseEntity.created(uri).body(planMapper.toPlanView(plan));
	}

	@Operation(summary = "Edit a specific plan")
	@PatchMapping ("/{name}")
	@RolesAllowed({AuthorityRole.MARKETING_DIRECTOR})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PlanDTO> editPlan(
			@PathVariable("name") String name,
			@Valid @RequestBody EditPlanRequest resource, HttpServletRequest request) throws Exception {

		String authorization = request.getHeader("Authorization");

		final var editPlan = service.editPlan(name, resource, authorization, false);

		return ResponseEntity.ok().body(planMapper.toPlanView(editPlan));
	}

	@Operation(summary = "Edit a specific plan")
	@PatchMapping ("/internal/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PlanDTO> editInternalPlan(
			@PathVariable("name") String name,
			@Valid @RequestBody EditPlanRequest request) throws Exception {


		final var editPlan = service.editPlan(name, request, null, true);

		return ResponseEntity.ok().body(planMapper.toPlanView(editPlan));
	}

	@Operation(summary = "Get a specific Plan")
	@GetMapping("/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PlanDTO> findSpecificPlan(
			@PathVariable("name") String name) throws Exception {
		Plan plan = service.findByName(name,false);
		return ResponseEntity.ok().body(planMapper.toPlanView(plan));

	}

	@Operation(summary = "Internal endpoint to get a specific plan")
	@GetMapping("/internal/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PlanDTO> findSpecificPlanInternal(
			@PathVariable("name") String name) throws Exception {
		Plan plan = service.findByName(name,true);
		return ResponseEntity.ok().body(planMapper.toPlanView(plan));
	}
}
