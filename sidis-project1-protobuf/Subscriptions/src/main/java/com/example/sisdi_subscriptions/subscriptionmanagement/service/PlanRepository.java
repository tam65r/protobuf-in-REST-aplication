package com.example.sisdi_subscriptions.subscriptionmanagement.service;

import com.example.sisdi_subscriptions.exceptions.InconsistencyDataException;
import org.apache.http.HttpStatus;

public interface PlanRepository {
    int checkIfPlanExists(String name) throws Exception;

    String checkIfPlanExistsResponse(String name) throws Exception;
}
