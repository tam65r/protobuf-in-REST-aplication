package com.example.sisdi_users.auth.api;

import com.example.sisdi_users.exceptions.InconsistencyDataException;
import com.example.sisdi_users.usermanagement.api.UserDTOMapper;
import com.example.sisdi_users.usermanagement.model.UserJPA;
import com.example.sisdi_users.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.sisdi_users.usermanagement.model.proto.UserEntity.User;
import javax.validation.Valid;
import java.net.URI;

import com.example.sisdi_users.usermanagement.api.proto.UserRequests.AuthRequest;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateUserRequest;

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/public")
public class AuthApi {
 	private final AuthenticationManager authenticationManager;

	private final JwtEncoder jwtEncoder;

	private final UserDTOMapper mapper;

	private final UserService service;


	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody @Valid final AuthRequest request) throws Exception {
		try {

			final String token = service.login(request, false);

			final UserJPA userJPA = service.getByUsername(request.getUsername(),false);

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(mapper.toDTOEntity(userJPA));
		} catch (final InconsistencyDataException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User> createInternal(@Valid @RequestBody final CreateUserRequest resource) throws Exception {
		UserJPA jpa = mapper.createJPA(resource);
		final var user = service.create(jpa, mapper.createSubscriptionRequest(resource));
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Failed to sign in user");
		}
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(String.valueOf(user.getId()))
				.build()
				.toUri();
		return ResponseEntity.created(uri).body(mapper.toDTOEntity(user));
	}
}


