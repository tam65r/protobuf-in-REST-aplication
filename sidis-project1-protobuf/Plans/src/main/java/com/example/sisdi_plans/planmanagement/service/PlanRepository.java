package com.example.sisdi_plans.planmanagement.service;


import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;


public interface PlanRepository {
    PlanJPA create(PlanJPA planJPA) throws Exception;
    PlanJPA findByName(String name, boolean internal) throws Exception ;
    PlanJPA changeActivityStatus(String name, boolean internal, String authorization) throws Exception;
    Iterable<PlanJPA> getAll(boolean internal) throws Exception;
    PlanJPA editPlan(String name, EditPlanRequest request, String authorization, boolean internal) throws Exception ;
}
