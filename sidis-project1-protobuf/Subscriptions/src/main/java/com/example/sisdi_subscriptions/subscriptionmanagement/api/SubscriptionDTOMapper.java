package com.example.sisdi_subscriptions.subscriptionmanagement.api;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.FeeType;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.PaymentMethod;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.proto.SubscriptionEntity.Subscription;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.proto.SubscriptionEntity.SubscriptionList;
import com.example.sisdi_subscriptions.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionDTOMapper {
    public SubscriptionJPA toJPAEntity(Subscription dto) {

        String subscriberID = dto.getSubscriberID();
        String plan = dto.getPlan();
        String initialDate = dto.getInitialDate();
        boolean isActive = dto.getIsActive();
        String finalDate = dto.getFinalDate();
        String endSubscriptionDate = dto.getEndSubscriptionDate();
        FeeType feeType = FeeType.fromInt(dto.getFeeTypeValue());
        PaymentMethod paymentMethod = PaymentMethod.fromInt(dto.getPaymentMethodValue());

        SubscriptionJPA jpa = new SubscriptionJPA(subscriberID,plan, Utils.parseString(initialDate),feeType,paymentMethod,Utils.parseString(finalDate));
        jpa.setActive(isActive);
        jpa.setEndSubscriptionDate(Utils.parseString(endSubscriptionDate));

        return  jpa;
    }

    public Subscription toDTOEntity(SubscriptionJPA jpaEntity) {
        return Subscription.newBuilder()
                .setSubscriberID(jpaEntity.getSubscriberID())
                .setPlan(jpaEntity.getPlan())
                .setInitialDate(jpaEntity.getInitialDate().toString())
                .setIsActive(jpaEntity.getStatus())
                .setFinalDate(jpaEntity.getFinalDate().toString())
                .setEndSubscriptionDate(jpaEntity.getEndSubscriptionDate().toString())
                .setFeeTypeValue(FeeType.toInt(jpaEntity.getFeeType()))
                .setPaymentMethodValue(PaymentMethod.toInt(jpaEntity.getPaymentMethod()))
                .build();
    }


    public List<Subscription> toDTOEntityList(List<SubscriptionJPA> jpaList) {
        List<Subscription> dtos = new ArrayList<>();
        for(SubscriptionJPA jpa: jpaList) {
            dtos.add(toDTOEntity(jpa));
        }
        return dtos;
    }

    public List<SubscriptionJPA> toJPAEntityList(List<Subscription> list) {
        List<SubscriptionJPA> jpa = new ArrayList<>();
        for(Subscription dto: list) {
            jpa.add(toJPAEntity(dto));
        }
        return jpa;
    }

    public SubscriptionList toDTOPlanList(List<Subscription> dtos) {
        return SubscriptionList.newBuilder()
                .addAllSubscriptions(dtos)
                .build();
    }
    public List<Subscription> toDTOEntityList(SubscriptionList list) {
        return list.getSubscriptionsList();
    }

}
