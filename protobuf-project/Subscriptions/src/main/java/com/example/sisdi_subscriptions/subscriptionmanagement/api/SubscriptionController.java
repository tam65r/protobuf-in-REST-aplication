package com.example.sisdi_subscriptions.subscriptionmanagement.api;



import com.example.sisdi_subscriptions.subscriptionmanagement.model.AuthorityRole;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.proto.SubscriptionEntity;
import com.example.sisdi_subscriptions.subscriptionmanagement.service.SubscriptionService;

import com.example.sisdi_subscriptions.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import com.example.sisdi_subscriptions.subscriptionmanagement.api.SubscriptionDTOMapper;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.proto.SubscriptionEntity.Subscription;
import com.example.sisdi_subscriptions.subscriptionmanagement.api.proto.SubscriptionRequests.CreateSubscriptionRequest;

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
	public ResponseEntity<Subscription> switchPlan(
			HttpServletRequest request,
			@PathVariable("plan") String plan) throws Exception {

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.switchPlan(username,plan, authorization, false);

		return ResponseEntity.ok().body(mapper.toDTOEntity(subscription));

	}


	@Operation(summary = "Switch plan (upgrade/downgrade)")
	@RolesAllowed({AuthorityRole.ADMIN,AuthorityRole.SUBSCRIBER})
	@PatchMapping("/internal/switch/{plan}")
	public ResponseEntity<Subscription> switchPlanInternal(
			HttpServletRequest request,
			@PathVariable("plan") String plan) throws Exception {

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.switchPlan(username,plan, authorization,true);

		return ResponseEntity.ok().body(mapper.toDTOEntity(subscription));

	}

	@Operation(summary = "Renew annual subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping("/renew")
	public ResponseEntity<Subscription> renewSubscription(HttpServletRequest request) throws Exception{

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.renewSubscription(username, authorization,false);

		return ResponseEntity.ok().body(mapper.toDTOEntity(subscription));
	}

	@Operation(summary = "Renew annual subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping("/internal/renew")
	public ResponseEntity<Subscription> renewSubscriptionInternal(HttpServletRequest request) throws Exception{

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.renewSubscription(username, authorization, true);

		return ResponseEntity.ok().body(mapper.toDTOEntity(subscription));
	}

	@Operation(summary =  "Create a new subscription")
	@PostMapping("/newSub")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Subscription> create(@Valid @RequestBody final CreateSubscriptionRequest resource) throws Exception {

		final var subscription = service.create(resource);

		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(String.valueOf(subscription.getId()))
				.build()
				.toUri();
		return ResponseEntity.created(uri).body(mapper.toDTOEntity(subscription));
	}

	@Operation(summary = "Cancels a subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping(value = "/cancel")
	public ResponseEntity<Subscription> deactivateSubscription(
			HttpServletRequest request) throws Exception{

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.cancel(username, authorization, false);

		return ResponseEntity.ok().body(mapper.toDTOEntity(subscription));
	}

	@Operation(summary = "Cancels a subscription")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@PatchMapping(value = "/internal/cancel")
	public ResponseEntity<Subscription> deactivateSubscriptionInternal(
			 HttpServletRequest request) throws Exception {

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var subscription = service.cancel(username, authorization, true);

		return ResponseEntity.ok().body(mapper.toDTOEntity(subscription));
	}

	@Operation(summary = "Get user plan details")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@GetMapping("/userPlanDetails")
	public ResponseEntity<byte []> getDetailsByUsername(HttpServletRequest request) throws Exception {
		MediaType protobufMediaType = new MediaType("application", "x-protobuf");

		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(protobufMediaType);

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var planBytes = service.getDetailsByUsername(username, authorization,false);

		return new ResponseEntity<byte []>(planBytes, httpHeaders, HttpStatus.OK);
	}

	@Operation(summary = "Get user plan details")
	@RolesAllowed({AuthorityRole.SUBSCRIBER, AuthorityRole.ADMIN})
	@GetMapping("/internal/userPlanDetails")
	public ResponseEntity<byte []> getDetailsByUsernameInternal(HttpServletRequest request) throws Exception {
		MediaType protobufMediaType = new MediaType("application", "x-protobuf");

		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(protobufMediaType);

		String username = utils.getEmailFromToken(request);
		String authorization = request.getHeader("Authorization");

		final var planBytes = service.getDetailsByUsername(username,authorization,true);

		return new ResponseEntity<byte []>(planBytes, httpHeaders, HttpStatus.OK);
	}

	@Operation(summary = "Retrieve subscription details")
	@RolesAllowed({AuthorityRole.ADMIN})
	@GetMapping("/details")
	public ResponseEntity<SubscriptionEntity.SubscriptionList> getSubscriptionDetailsByPlan( HttpServletRequest request, @RequestParam String plan) throws Exception {
		String authorization = request.getHeader("Authorization");

		final var subscriptions = service.getSubscriptionDetailsByPlan(plan, authorization, false);

		return ResponseEntity.ok().body(mapper.toDTOPlanList(mapper.toDTOEntityList(subscriptions)));
	}

	@Operation(summary = "Internal retrieve subscription details")
	@RolesAllowed({AuthorityRole.ADMIN})
	@GetMapping("/internal/details")
	public ResponseEntity<SubscriptionEntity.SubscriptionList> getSubscriptionDetailsByPlanInternal(@RequestParam String plan) throws Exception {

		final var subscriptions = service.getSubscriptionDetailsByPlan(plan, null, true);

		return ResponseEntity.ok().body(mapper.toDTOPlanList(mapper.toDTOEntityList(subscriptions)));
	}

}
