package com.example.sisdi_users.usermanagement.api;

import com.example.sisdi_users.exceptions.InconsistencyDataException;

import com.example.sisdi_users.usermanagement.model.UserJPA;
import com.example.sisdi_users.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import com.example.sisdi_users.usermanagement.model.proto.UserEntity.User;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.AuthRequest;

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
	public ResponseEntity<User> getByUsername(@PathVariable("username") String username)  throws Exception{
		UserJPA userJPA = service.getByUsername(username, false);
		return ResponseEntity.ok().body(mapper.toDTOEntity(userJPA));
	}
	@Operation(summary = "Endpoint to verify if user exist in other instance")
	@GetMapping("/internal/{username}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<User> getByUsernameInternal(@PathVariable("username") String username)  throws Exception{
		UserJPA userJPA = service.getByUsername(username, true);
		return ResponseEntity.ok().body(mapper.toDTOEntity(userJPA));
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
