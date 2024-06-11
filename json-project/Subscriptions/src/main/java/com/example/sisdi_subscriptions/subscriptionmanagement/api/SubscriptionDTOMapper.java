package com.example.sisdi_subscriptions.subscriptionmanagement.api;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;

import java.util.List;

public abstract class SubscriptionDTOMapper {
    public abstract SubscriptionDTO toSubscriptionDTO (Subscription sub);

    public abstract List<SubscriptionDTO> toListSubscriptionDTO (List<Subscription> sub);
}
