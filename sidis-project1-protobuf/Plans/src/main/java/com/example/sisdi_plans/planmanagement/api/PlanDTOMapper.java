package com.example.sisdi_plans.planmanagement.api;

import com.example.sisdi_plans.planmanagement.model.MusicSuggestions;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
import com.example.sisdi_plans.planmanagement.model.proto.PlanEntity.Plan;
import com.example.sisdi_plans.planmanagement.model.proto.PlanEntity.PlanList;
import org.springframework.stereotype.Component;
import com.example.sisdi_plans.planmanagement.api.proto.PlanRequests.CreatePlanRequest;
import com.example.sisdi_plans.planmanagement.api.proto.PlanRequests.EditPlanRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlanDTOMapper {
    public PlanJPA toJPAEntity(Plan dto) {

        String name = dto.getName();
        String description = dto.getDescription();
        MusicSuggestions musicSuggestions = MusicSuggestions.fromInt(dto.getMusicSuggestionsValue());
        int musicCollections = dto.getMusicCollections();
        float annualFee = dto.getAnnualFee();
        float monthlyFee = dto.getMonthlyFee();
        int numberOfMinutes = dto.getNumberOfMinutes();

        PlanJPA jpaEntity = new PlanJPA(name,description,numberOfMinutes,musicSuggestions,musicCollections,monthlyFee,annualFee);
        jpaEntity.setActive(dto.getIsActive());
        return jpaEntity ;
    }

    public Plan toDTOEntity(PlanJPA jpaEntity) {
        return Plan.newBuilder()
                .setName(jpaEntity.getName())
                .setDescription(jpaEntity.getDescription())
                .setIsActive(jpaEntity.getIsActive())
                .setMusicCollections(jpaEntity.getMusicCollections())
                .setAnnualFee(jpaEntity.getAnnualFee())
                .setMonthlyFee(jpaEntity.getMonthlyFee())
                .setMusicSuggestionsValue(MusicSuggestions.toInt(jpaEntity.getMusicSuggestions()))
                .setNumberOfMinutes(jpaEntity.getNumberOfMinutes())
                .build();
    }


    public List<Plan> toDTOEntityList(List<PlanJPA> jpaList) {
        List<Plan> dtos = new ArrayList<>();
        for(PlanJPA jpa: jpaList) {
            dtos.add(toDTOEntity(jpa));
        }
        return dtos;
    }

    public List<PlanJPA> toJPAEntityList(List<Plan> list) {
        List<PlanJPA> jpa = new ArrayList<>();
        for(Plan dto: list) {
            jpa.add(toJPAEntity(dto));
        }
        return jpa;
    }

    public PlanList toDTOPlanList(List<Plan> dtos) {
        return PlanList.newBuilder()
                .addAllPlan(dtos)
                .build();
    }
    public List<Plan> toDTOEntityList(PlanList list) {
        return list.getPlanList();
    }

    public PlanJPA createJPA(CreatePlanRequest resource) {
        String name = resource.getName();
        String description = resource.getDescription();
        String musicSuggestions = resource.getMusicSuggestions();
        int musicCollections = resource.getMusicCollections();
        float annualFee = resource.getAnnualFee();
        float monthlyFee = resource.getMonthlyFee();
        int numberOfMinutes = resource.getNumberOfMinutes();

        return new PlanJPA(name, description, numberOfMinutes, MusicSuggestions.fromString(musicSuggestions), musicCollections, monthlyFee, annualFee);
    }

}
