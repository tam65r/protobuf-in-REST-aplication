package com.example.sisdi_subscriptions.subscriptionmanagement.api;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;

public abstract class SubscriptionDTOMapper {
    public abstract SubscriptionDTO toSubscriptionDTO (SubscriptionJPA sub);
}
