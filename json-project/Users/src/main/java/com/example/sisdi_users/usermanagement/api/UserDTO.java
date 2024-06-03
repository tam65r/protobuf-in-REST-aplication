package com.example.sisdi_users.usermanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A Subscriber")
public class UserDTO {

	private String username;

	private String name;

	private String citizenCardNumber;

	private String birthday;

	private String phoneNumber;

	private String sex;
}
