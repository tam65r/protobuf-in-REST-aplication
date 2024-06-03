package com.example.sisdi_subscriptions.subscriptionmanagement.service;


import com.example.sisdi_subscriptions.subscriptionmanagement.api.CreateSubscriptionRequest;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;



public interface SubscriptionRepository {
    Subscription create(CreateSubscriptionRequest resource) throws Exception;

    Subscription switchPlan(String username, String plan, String authorization, boolean internal) throws Exception;


    Subscription cancel(String username, String authorization, boolean internal) throws Exception;

    Subscription renewSubscription(String username, String authorization, boolean internal) throws Exception;

    String getDetailsByUsername(String username, String authorization, boolean internal) throws Exception;

}
