package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;

import com.example.sisdi_subscriptions.exceptions.InconsistencyDataException;
import com.example.sisdi_subscriptions.subscriptionmanagement.service.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlanRepositoryImpl implements PlanRepository {
    final PlanHTTPRepository httpRepository;


    public int checkIfPlanExists(String name) throws Exception {
        if (httpRepository.checkIfPlanExists(name) != HttpStatus.SC_OK) {
            throw new InconsistencyDataException(name);
        }
        return HttpStatus.SC_OK;
    }

    public byte [] checkIfPlanExistsResponse(String name) throws Exception {
        return httpRepository.checkIfPlanExistsResponse(name);

    }
}
