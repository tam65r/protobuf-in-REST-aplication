package com.example.sisdi_users.usermanagement.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriberRequest {

	@NotNull
	@NotBlank
	@Email
	@Size(min = 1, max = 128)
	private String username;

	@NotNull
	private String plan;

	@Nullable
	private String feeType;

	@Nullable
	private String paymentMethod;

	@Nullable
	private String initialDate;

}
