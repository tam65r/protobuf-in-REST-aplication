package com.example.sisdi_plans.planmanagement.service;

import com.example.sisdi_plans.planmanagement.api.CreatePlanRequest;
import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.api.PlanDTOMapper;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlanService {

	private final PlanDTOMapper mapper;

	private final PlanRepository planRepository;

	public Iterable<PlanJPA> getAll(boolean internal) throws Exception {
		if (!internal) {
			return planRepository.getAll(false);
		} else {
			return planRepository.getAll(true);
		}
	}


	public PlanJPA findByName(String name, boolean internal) throws Exception {
		if (!internal) {
			return planRepository.findByName(name,false);
		} else {
			return planRepository.findByName(name,true);
		}
	}

	public PlanJPA create(final CreatePlanRequest resource) throws Exception{

		final PlanJPA planJPA = mapper.create(resource);

		return planRepository.create(planJPA);
	}

	public PlanJPA changeActivityStatus(String name, String authorization, boolean internal) throws Exception {
		if (!internal) {
			return planRepository.changeActivityStatus(name, false,authorization);
		} else {
			return planRepository.changeActivityStatus(name, true,null);
		}
	}

	public PlanJPA editPlan(String name, EditPlanRequest request, String authorization, boolean internal) throws Exception {
		if (!internal) {
			return planRepository.editPlan(name,request,authorization,false);
		} else {
			return planRepository.editPlan(name,request,null,true);
		}
	}
}

