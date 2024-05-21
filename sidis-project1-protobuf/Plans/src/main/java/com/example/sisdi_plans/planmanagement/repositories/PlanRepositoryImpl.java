package com.example.sisdi_plans.planmanagement.repositories;

import com.example.sisdi_plans.exceptions.DuplicatedDataException;
import com.example.sisdi_plans.exceptions.NotFoundException;
import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
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
    public PlanJPA create(PlanJPA planJPA) throws Exception{

        Optional<PlanJPA> planDB = dbRepository.findByName(planJPA.getName());

        if (planDB.isEmpty()) {
            PlanJPA planJPAHTTP = httpRepository.getPlanByName(planJPA.getName());
            if (planJPAHTTP == null) {
                return dbRepository.save(planJPA);
            }
        }

        throw new DuplicatedDataException(PlanJPA.class, planJPA.getName());
    }

    @Override
    public PlanJPA findByName(String name, boolean internal) throws Exception {

        Optional<PlanJPA> planDB = dbRepository.findByName(name);

        if (planDB.isPresent()) {
            return planDB.get();
        }

        if (!internal) {
            PlanJPA planJPA = httpRepository.getPlanByName(name);

            if (planJPA != null) {
                return planJPA;
            }
        }

        throw new NotFoundException(PlanJPA.class,name);
    }

    @Override
    public PlanJPA changeActivityStatus(String name, boolean internal, String authorization) throws Exception {
        Optional<PlanJPA> planDB = dbRepository.findByName(name);

        if (planDB.isPresent()) {
            PlanJPA plDB = planDB.get();
            plDB.changeActivityStatus();
            return dbRepository.save(plDB);
        }

        if (!internal) {
            PlanJPA planJPAHTTP = httpRepository.deactivePlan(name, authorization);

            if (planJPAHTTP != null) {
                return planJPAHTTP;
            }
        }

        throw new NotFoundException(PlanJPA.class, "Name: " + name + " Not valid\n Please provide a valid plan");
    }

    @Override
    public Iterable<PlanJPA> getAll (boolean internal) throws Exception{
        Iterable<PlanJPA> plansDB = dbRepository.findAllByStatus();

        if (internal) {
            return plansDB;
        }

        ArrayList<PlanJPA> plansHTTP = httpRepository.getAllPlans();

        for (int i = 0; i< plansHTTP.toArray().length; i++) {
            plansHTTP.get(i).setActive(true);
        }

        for (PlanJPA planJPAToAdd : plansDB) {
            plansHTTP.add(planJPAToAdd);
        }

        return plansHTTP;
    }

    @Override
    public PlanJPA editPlan(String name, EditPlanRequest request, String authorization, boolean internal) throws Exception {

        final var planDB = dbRepository.findByName(name);

        if (planDB.isPresent()) {
            PlanJPA temp = planDB.get();
            temp.applyPatch(request.getDescription(),request.getNumberOfMinutes(),request.getMusicCollections(),request.getMusicSuggestions());
            return dbRepository.save(temp);
        }

        if (!internal) {
            PlanJPA planJPAHTTP = httpRepository.editPlanInternal(name, request, authorization);
            if (planJPAHTTP != null) {
                return planJPAHTTP;
            }
        }
        throw  new NotFoundException(PlanJPA.class, "Name: " + name + " Not valid\n Please provide a valid plan");

    }
}
