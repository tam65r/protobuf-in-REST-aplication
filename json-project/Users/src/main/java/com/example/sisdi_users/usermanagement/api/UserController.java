package com.example.sisdi_users.usermanagement.api;

import com.example.sisdi_users.auth.api.AuthRequest;
import com.example.sisdi_users.exceptions.InconsistencyDataException;
import com.example.sisdi_users.exceptions.NotFoundException;

import com.example.sisdi_users.usermanagement.model.User;
import com.example.sisdi_users.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Tag(name = "Users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService service;

	private final UserDTOMapper mapper;


	@Operation(summary = "Endpoint to get a user")
	@GetMapping("/{username}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UserDTO> getByUsername(@PathVariable("username") String username)  throws Exception{
		User user = service.getByUsername(username, false);
		return ResponseEntity.ok().body(mapper.toUserView(user));
	}
	@Operation(summary = "Endpoint to verify if user exist in other instance")
	@GetMapping("/internal/{username}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UserDTO> getByUsernameInternal(@PathVariable("username") String username)  throws Exception{
		User user = service.getByUsername(username, true);
		return ResponseEntity.ok().body(mapper.toUserView(user));
	}


	@PostMapping("/internal/login")
	public ResponseEntity<String> login(@RequestBody @Valid final AuthRequest request) throws Exception {
		try {
			String auth = service.login(request,true);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(auth);
		} catch (final InconsistencyDataException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
