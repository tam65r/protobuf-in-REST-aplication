package com.example.sisdi_plans.planmanagement.api;


import com.example.sisdi_plans.planmanagement.model.MusicSuggestions;
import com.example.sisdi_plans.planmanagement.model.Plan;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;

@Generated(
	value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PlanDTOMapperImpl extends PlanDTOMapper {

	@Override
	public PlanDTO toPlanView(Plan plan) {
		if (plan == null) {
			return null;
		}

		PlanDTO planDTO = new PlanDTO();

		planDTO.setName(plan.getName());
		planDTO.setDescription(plan.getDescription());
		planDTO.setNumberOfMinutes(plan.getNumberOfMinutes());
		planDTO.setMusicCollections(plan.getMusicCollections());
		planDTO.setMusicSuggestions(plan.getMusicSuggestions().toString());
		planDTO.setAnnualFee(plan.getAnnualFee());
		planDTO.setMonthlyFee(plan.getMonthlyFee());
		planDTO.setActive(plan.getIsActive());

		return planDTO;
	}

	@Override
	public Iterable<PlanDTO> toPlanView(Iterable<Plan> plans) {
		if ( plans == null ) {
			return null;
		}

		ArrayList<PlanDTO> iterable = new ArrayList<PlanDTO>();
		for ( Plan plan : plans ) {
			iterable.add( toPlanView( plan ) );
		}

		return iterable;
	}

	@Override
	public Plan create(CreatePlanRequest resource) {
		if (resource == null) {
			return null;
		}

		String name = resource.getName();
		String musicSuggestions = resource.getMusicSuggestions();
		int musicCollections = resource.getMusicCollections();
		String description = resource.getDescription();
		float anualFee = resource.getAnnualFee();
		float monthlyFee = resource.getMonthlyFee();
		int numberOfMinutes = resource.getNumberOfMinutes();

		Plan plan = new Plan(name,description,numberOfMinutes, MusicSuggestions.fromString(musicSuggestions),musicCollections,monthlyFee,anualFee);

		return plan;
	}
}
