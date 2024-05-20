package com.example.sisdi_users.usermanagement.api;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

	@NotNull
	@NotBlank
	@Email
	@Size(min = 1, max = 128)
	private String username;

	@NotNull
	@NotBlank
	private String password;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 32)
	private   String name;

	@NotNull
	@NotBlank
	@Size(max = 9)
	private String citizenCardNumber;

	@NotNull
	@NotBlank
	@Size(max = 12)
	private String birthday;

	@NotNull
	@NotBlank
	@Size(max = 9)
	private String phoneNumber;

	@NotNull
	@NotBlank
	@Size(max = 16)
	private String sex;

	@NotNull
	@NotBlank
	private String role;

	@Nullable
	private String plan;

	@Nullable
	private String feeType;

	@Nullable
	private String paymentMethod;

	@Nullable
	private String initialDate;

}
