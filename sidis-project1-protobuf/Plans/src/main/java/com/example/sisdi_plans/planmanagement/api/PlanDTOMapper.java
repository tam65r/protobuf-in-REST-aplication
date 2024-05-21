package com.example.sisdi_plans.planmanagement.api;

import com.example.sisdi_plans.planmanagement.model.PlanJPA;


public abstract class PlanDTOMapper {
	public abstract PlanDTO toPlanView (PlanJPA planJPA);

	public abstract Iterable<PlanDTO> toPlanView(Iterable<PlanJPA> plans);

	public abstract PlanJPA create(CreatePlanRequest resource);
}
