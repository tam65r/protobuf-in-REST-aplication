package com.example.sisdi_users.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class Utils {

	@Autowired
	private JwtDecoder jwtDecoder;


	public static LocalDateTime parseString(String stringDate) {
		String[] parts = stringDate.split("-");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Invalid date format");
		}
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);


		return LocalDateTime.of(year,month,day,0,0);
	}

	public String getEmailFromToken(HttpServletRequest request) {
		String token = request.getHeader("authorization");
		String newToken = token.replace("Bearer ", "");
		Jwt decodedToken = this.jwtDecoder.decode(newToken);
		String subject = (String) decodedToken.getClaims().get("sub");

		String[] parts = subject.split(",");
		if (parts.length > 1) {
			return parts[1];
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Username!");
		}
	}
}
