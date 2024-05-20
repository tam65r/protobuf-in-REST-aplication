package com.example.sisdi_users.auth.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	@NotNull
	@Email
	private String username;

	@NotNull
	private String password;
}
