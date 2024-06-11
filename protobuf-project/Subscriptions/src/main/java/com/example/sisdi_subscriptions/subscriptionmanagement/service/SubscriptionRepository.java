package com.example.sisdi_subscriptions.subscriptionmanagement.service;


import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;
import com.example.sisdi_subscriptions.subscriptionmanagement.api.proto.SubscriptionRequests.CreateSubscriptionRequest;

import java.util.List;

public interface SubscriptionRepository {
    SubscriptionJPA create(CreateSubscriptionRequest resource) throws Exception;

    SubscriptionJPA switchPlan(String username, String plan, String authorization, boolean internal) throws Exception;


    SubscriptionJPA cancel(String username, String authorization, boolean internal) throws Exception;

    SubscriptionJPA renewSubscription(String username, String authorization, boolean internal) throws Exception;

    byte [] getDetailsByUsername(String username, String authorization, boolean internal) throws Exception;

    List<SubscriptionJPA> subscriptionDetailsByPlan(String plan, String authorization, boolean internal) throws Exception;

}
