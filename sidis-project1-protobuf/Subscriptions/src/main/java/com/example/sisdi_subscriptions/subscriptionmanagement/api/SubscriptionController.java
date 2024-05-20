package com.example.sisdi_subscriptions.subscriptionmanagement.api;



import com.example.sisdi_subscriptions.subscriptionmanagement.model.AuthorityRole;
import com.example.sisdi_subscriptions.subscriptionmanagement.service.SubscriptionService;

import com.example.sisdi_subscriptions.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;


@Tag(name = "Subscriptions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

	private final SubscriptionService service;

	private final SubscriptionDTOMapper mapper;

	private final Utils utils;

	@Operation(summary = "Switch plan (upgrade/downgrade)")
	@RolesAllowed({AuthorityRole.ADMIN,AuthorityRole.SUBSCRIBER})
	@PatchMapping("/switch/{plan}")
	public ResponseEntity<?> switchPlan(
			HttpServletRequest request,
			@PathVariable("plan") String plan) throws Exception {

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.switchPlan(username,plan, authorization, false);

		return ResponseEntity.ok().body(mapper.toSubscriptionDTO(subscription));

	}


	@Operation(summary = "Switch plan (upgrade/downgrade)")
	@RolesAllowed({AuthorityRole.ADMIN,AuthorityRole.SUBSCRIBER})
	@PatchMapping("/internal/switch/{plan}")
	public ResponseEntity<?> switchPlanInternal(
			HttpServletRequest request,
			@PathVariable("plan") String plan) throws Exception {

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.switchPlan(username,plan, authorization,true);

		return ResponseEntity.ok().body(mapper.toSubscriptionDTO(subscription));

	}

	@Operation(summary = "Renew annual subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping("/renew")
	public ResponseEntity<?> renewSubscription(HttpServletRequest request) throws Exception{

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.renewSubscription(username, authorization,false);

		return ResponseEntity.ok().body(subscription);
	}

	@Operation(summary = "Renew annual subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping("/internal/renew")
	public ResponseEntity<?> renewSubscriptionInternal(HttpServletRequest request) throws Exception{

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.renewSubscription(username, authorization, true);

		return ResponseEntity.ok().body(subscription);
	}

	@Operation(summary =  "Create a new subscription")
	@PostMapping("/newSub")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SubscriptionDTO> create(@Valid @RequestBody final CreateSubscriptionRequest resource) throws Exception {

		final var subscription = service.create(resource);

		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(String.valueOf(subscription.getId()))
				.build()
				.toUri();
		return ResponseEntity.created(uri).body(mapper.toSubscriptionDTO(subscription));
	}

	@Operation(summary = "Cancels a subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping(value = "/cancel")
	public ResponseEntity<SubscriptionDTO> deactivateSubscription(
			HttpServletRequest request) throws Exception{

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.cancel(username, authorization, false);

		return ResponseEntity.ok().body(mapper.toSubscriptionDTO(subscription));
	}

	@Operation(summary = "Cancels a subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping(value = "/internal/cancel")
	public ResponseEntity<SubscriptionDTO> deactivateSubscriptionInternal(
			 HttpServletRequest request) throws Exception {

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.cancel(username, authorization, true);

		return ResponseEntity.ok().body(mapper.toSubscriptionDTO(subscription));
	}

	@Operation(summary = "Get user plan details")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@GetMapping("/userPlanDetails")
	public ResponseEntity<String> getDetailsByUsername(HttpServletRequest request) throws Exception {

		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var plan = service.getDetailsByUsername(username, authorization,false);

		return new ResponseEntity<String>(plan, httpHeaders, HttpStatus.OK);
	}

	@Operation(summary = "Get user plan details")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@GetMapping("/internal/userPlanDetails")
	public ResponseEntity<String> getDetailsByUsernameInternal(HttpServletRequest request) throws Exception {

		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var plan = service.getDetailsByUsername(username,authorization,true);

		return new ResponseEntity<String>(plan, httpHeaders, HttpStatus.OK);
	}

}
