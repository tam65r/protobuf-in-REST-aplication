package com.example.sisdi_plans.planmanagement.repositories;

import com.example.sisdi_plans.exceptions.DuplicatedDataException;
import com.example.sisdi_plans.exceptions.NotFoundException;
import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.model.Plan;
import com.example.sisdi_plans.planmanagement.repositories.PlanHTTPRepository;
import com.example.sisdi_plans.planmanagement.repositories.PlanDBRepository;
import com.example.sisdi_plans.planmanagement.service.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanDBRepository dbRepository;
    private final PlanHTTPRepository httpRepository;

    @Override
    public Plan create(Plan plan) throws Exception{

        Optional<Plan> planDB = dbRepository.findByName(plan.getName());

        if (planDB.isEmpty()) {
            Plan planHTTP = httpRepository.getPlanByName(plan.getName());
            if (planHTTP == null) {
                return dbRepository.save(plan);
            }
        }

        throw new DuplicatedDataException(Plan.class,plan.getName());
    }

    @Override
    public Plan findByName(String name, boolean internal) throws Exception {

        Optional<Plan> planDB = dbRepository.findByName(name);

        if (planDB.isPresent()) {
            return planDB.get();
        }

        if (!internal) {
            Plan plan = httpRepository.getPlanByName(name);

            if (plan != null) {
                return plan;
            }
        }

        throw new NotFoundException(Plan.class,name);
    }

    @Override
    public Plan changeActivityStatus(String name, boolean internal, String authorization) throws Exception {
        Optional<Plan> planDB = dbRepository.findByName(name);

        if (planDB.isPresent()) {
            Plan plDB = planDB.get();
            plDB.changeActivityStatus();
            return dbRepository.save(plDB);
        }

        if (!internal) {
            Plan planHTTP = httpRepository.deactivePlan(name, authorization);

            if (planHTTP != null) {
                return planHTTP;
            }
        }

        throw new NotFoundException(Plan.class, "Name: " + name + " Not valid\n Please provide a valid plan");
    }

    @Override
    public Iterable<Plan> getAll (boolean internal) throws Exception{
        Iterable<Plan> plansDB = dbRepository.findAllByStatus();

        if (internal) {
            return plansDB;
        }

        ArrayList<Plan> plansHTTP = httpRepository.getAllPlans();

        for (int i = 0; i< plansHTTP.toArray().length; i++) {
            plansHTTP.get(i).setActive(true);
        }

        for (Plan planToAdd : plansDB) {
            plansHTTP.add(planToAdd);
        }

        return plansHTTP;
    }

    @Override
    public Plan editPlan(String name, EditPlanRequest request, String authorization, boolean internal) throws Exception {

        final var planDB = dbRepository.findByName(name);

        if (planDB.isPresent()) {
            Plan temp = planDB.get();
            temp.applyPatch(request.getDescription(),request.getNumberOfMinutes(),request.getMusicCollections(),request.getMusicSuggestions());
            return dbRepository.save(temp);
        }

        if (!internal) {
            Plan planHTTP = httpRepository.editPlanInternal(name, request, authorization);
            if (planHTTP != null) {
                return planHTTP;
            }
        }
        throw  new NotFoundException(Plan.class, "Name: " + name + " Not valid\n Please provide a valid plan");

    }
}
