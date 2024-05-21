package com.example.sisdi_subscriptions.subscriptionmanagement.service;


import com.example.sisdi_subscriptions.subscriptionmanagement.api.CreateSubscriptionRequest;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;


public interface SubscriptionRepository {
    SubscriptionJPA create(CreateSubscriptionRequest resource) throws Exception;

    SubscriptionJPA switchPlan(String username, String plan, String authorization, boolean internal) throws Exception;


    SubscriptionJPA cancel(String username, String authorization, boolean internal) throws Exception;

    SubscriptionJPA renewSubscription(String username, String authorization, boolean internal) throws Exception;

    String getDetailsByUsername(String username, String authorization, boolean internal) throws Exception;

}
