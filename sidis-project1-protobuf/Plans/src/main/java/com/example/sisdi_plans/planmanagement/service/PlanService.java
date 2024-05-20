package com.example.sisdi_plans.planmanagement.service;

import com.example.sisdi_plans.planmanagement.api.CreatePlanRequest;
import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.api.PlanDTOMapper;
import com.example.sisdi_plans.planmanagement.model.Plan;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class PlanService {

	private final PlanDTOMapper mapper;

	private final PlanRepository planRepository;

	public Iterable<Plan> getAll(boolean internal) throws Exception {
		if (!internal) {
			return planRepository.getAll(false);
		} else {
			return planRepository.getAll(true);
		}
	}


	public Plan findByName(String name, boolean internal) throws Exception {
		if (!internal) {
			return planRepository.findByName(name,false);
		} else {
			return planRepository.findByName(name,true);
		}
	}

	public Plan create(final CreatePlanRequest resource) throws Exception{

		final Plan plan = mapper.create(resource);

		return planRepository.create(plan);
	}

	public Plan changeActivityStatus(String name, String authorization, boolean internal) throws Exception {
		if (!internal) {
			return planRepository.changeActivityStatus(name, false,authorization);
		} else {
			return planRepository.changeActivityStatus(name, true,null);
		}
	}

	public Plan editPlan(String name, EditPlanRequest request, String authorization, boolean internal) throws Exception {
		if (!internal) {
			return planRepository.editPlan(name,request,authorization,false);
		} else {
			return planRepository.editPlan(name,request,null,true);
		}
	}
}

