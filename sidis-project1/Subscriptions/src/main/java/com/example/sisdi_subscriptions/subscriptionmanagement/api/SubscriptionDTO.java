package com.example.sisdi_subscriptions.subscriptionmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A Subscription")
public class SubscriptionDTO {

    private String username;

    private String plan;

    private String paymentMethod;

    private String FeeType;

    private String initialDate;

}
