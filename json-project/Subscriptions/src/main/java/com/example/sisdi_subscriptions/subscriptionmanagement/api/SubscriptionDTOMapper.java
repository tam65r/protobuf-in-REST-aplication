package com.example.sisdi_subscriptions.subscriptionmanagement.api;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;

public abstract class SubscriptionDTOMapper {
    public abstract SubscriptionDTO toSubscriptionDTO (Subscription sub);
}
