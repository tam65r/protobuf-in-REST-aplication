package com.example.sisdi_plans.planmanagement.api;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanRequest {

	@NotNull
	@NotBlank
	@Size(min = 1, max = 32)
	private String name;

	@NotNull
	@NotBlank
	@Size(min = 0, max = 2048)
	private String description;

	@NotNull
	private int numberOfMinutes;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 16)
	private String musicSuggestions;

	@NotNull
	private int musicCollections;

	@NotNull
	private float monthlyFee;

	@NotNull
	private float annualFee;
}
