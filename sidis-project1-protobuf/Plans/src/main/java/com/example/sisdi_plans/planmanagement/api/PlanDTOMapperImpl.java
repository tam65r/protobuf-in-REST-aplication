package com.example.sisdi_plans.planmanagement.api;


import com.example.sisdi_plans.planmanagement.model.MusicSuggestions;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;

@Generated(
	value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PlanDTOMapperImpl extends PlanDTOMapper {

	@Override
	public PlanDTO toPlanView(PlanJPA planJPA) {
		if (planJPA == null) {
			return null;
		}

		PlanDTO planDTO = new PlanDTO();

		planDTO.setName(planJPA.getName());
		planDTO.setDescription(planJPA.getDescription());
		planDTO.setNumberOfMinutes(planJPA.getNumberOfMinutes());
		planDTO.setMusicCollections(planJPA.getMusicCollections());
		planDTO.setMusicSuggestions(planJPA.getMusicSuggestions().toString());
		planDTO.setAnnualFee(planJPA.getAnnualFee());
		planDTO.setMonthlyFee(planJPA.getMonthlyFee());
		planDTO.setActive(planJPA.getIsActive());

		return planDTO;
	}

	@Override
	public Iterable<PlanDTO> toPlanView(Iterable<PlanJPA> plans) {
		if ( plans == null ) {
			return null;
		}

		ArrayList<PlanDTO> iterable = new ArrayList<PlanDTO>();
		for ( PlanJPA planJPA : plans ) {
			iterable.add( toPlanView(planJPA) );
		}

		return iterable;
	}

	@Override
	public PlanJPA create(CreatePlanRequest resource) {
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

		PlanJPA planJPA = new PlanJPA(name,description,numberOfMinutes, MusicSuggestions.fromString(musicSuggestions),musicCollections,monthlyFee,anualFee);

		return planJPA;
	}
}
