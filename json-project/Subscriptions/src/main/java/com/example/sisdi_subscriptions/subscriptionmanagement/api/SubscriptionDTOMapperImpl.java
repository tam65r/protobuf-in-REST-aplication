package com.example.sisdi_subscriptions.subscriptionmanagement.api;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class SubscriptionDTOMapperImpl extends SubscriptionDTOMapper{


    @Override
    public SubscriptionDTO toSubscriptionDTO(Subscription sub) {
        if (sub == null) {
            return null;
        }

        SubscriptionDTO dto = new SubscriptionDTO();

        dto.setPlan(sub.getPlan());
        dto.setInitialDate(sub.getInitialDate().toString());
        dto.setFeeType(sub.getFeeType().toString());
        dto.setPaymentMethod(sub.getPaymentMethod().toString());
        dto.setUsername(sub.getSubscriberID());

        return dto;
    }

    @Override
    public List<SubscriptionDTO> toListSubscriptionDTO(List<Subscription> sub) {
        List<SubscriptionDTO> dto = new ArrayList<>();
        for(Subscription jpa : sub) {
            dto.add(toSubscriptionDTO(jpa));
        }

        return dto;
    }


}
