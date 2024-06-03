package com.example.sisdi_plans.planmanagement.api;

import com.example.sisdi_plans.planmanagement.model.Plan;


public abstract class PlanDTOMapper {
	public abstract PlanDTO toPlanView (Plan plan);

	public abstract Iterable<PlanDTO> toPlanView(Iterable<Plan> plans);

	public abstract Plan create(CreatePlanRequest resource);
}
