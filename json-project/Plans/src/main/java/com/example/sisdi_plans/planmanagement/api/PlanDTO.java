package com.example.sisdi_plans.planmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A Plan")
public class PlanDTO {

	private String name;

	private String description;

	private int numberOfMinutes;

	private String musicSuggestions;

	private int musicCollections;

	private float monthlyFee;

	private float annualFee;

	private boolean isActive;
}
