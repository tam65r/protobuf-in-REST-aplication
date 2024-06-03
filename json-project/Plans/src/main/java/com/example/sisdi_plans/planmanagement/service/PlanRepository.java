package com.example.sisdi_plans.planmanagement.service;


import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.model.Plan;


public interface PlanRepository {
    Plan create(Plan plan) throws Exception;
    Plan findByName(String name, boolean internal) throws Exception ;
    Plan changeActivityStatus(String name, boolean internal, String authorization) throws Exception;
    Iterable<Plan> getAll(boolean internal) throws Exception;
    Plan editPlan(String name, EditPlanRequest request, String authorization, boolean internal) throws Exception ;
}
