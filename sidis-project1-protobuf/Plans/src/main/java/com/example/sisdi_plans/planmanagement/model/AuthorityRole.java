package com.example.sisdi_plans.planmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

@Value
@AllArgsConstructor
public class AuthorityRole implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	public static final String ADMIN = "ADMIN";
	public static final String MARKETING_DIRECTOR = "MARKETING_DIRECTOR";
	public static final String SUBSCRIBER = "SUBSCRIBER";

	private String authority;
}
