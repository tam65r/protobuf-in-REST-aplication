package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;


import com.example.sisdi_subscriptions.exceptions.DuplicatedDataException;
import com.example.sisdi_subscriptions.exceptions.InconsistencyDataException;
import com.example.sisdi_subscriptions.exceptions.NotFoundException;
import com.example.sisdi_subscriptions.subscriptionmanagement.api.CreateSubscriptionRequest;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.FeeType;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.PaymentMethod;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;
import com.example.sisdi_subscriptions.subscriptionmanagement.service.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SubscriberRepositoryImpl implements SubscriptionRepository {

    final SubscriptionDBRepository dbRepository;
    final SubscriptionHTTPRepository httpRepository;
    final PlanRepositoryImpl driver;

    @Override
    public Subscription create(CreateSubscriptionRequest resource) throws Exception {
        String plan = resource.getPlan();
        Subscription subscription = new Subscription();

        if (dbRepository.findBySubscriberID(resource.getUsername()).isPresent()) {
            throw new DuplicatedDataException(Subscription.class,resource.getUsername());
        }

        if (plan.equals("Free")) {
            subscription.setPlan("Free");
            subscription.setFeeType(FeeType.NA);
            subscription.setPaymentMethod(PaymentMethod.NA);
        } else {
            subscription.setPlan(plan);
            if (resource.getFeeType() == null || resource.getPaymentMethod() == null) {
                throw new InconsistencyDataException(Subscription.class,"Null not a valid value");
            }
            if (resource.getFeeType().equals(FeeType.NA.toString()) || resource.getPaymentMethod().equals(PaymentMethod.NA.toString())) {
                throw new InconsistencyDataException(Subscription.class,"Null not a valid value");
            }

            subscription.setFeeType(FeeType.fromString(resource.getFeeType()));
            subscription.setPaymentMethod(PaymentMethod.fromString(resource.getPaymentMethod()));
        }

        LocalDateTime initialDateTime = LocalDateTime.now();

        LocalDateTime endDateTime = null;

        if (subscription.getFeeType() == FeeType.MONTHLY) {
            endDateTime = initialDateTime.plusMonths(1);
        } else if (subscription.getFeeType() == FeeType.ANNUAL) {
            endDateTime = initialDateTime.plusYears(1);
        }


        subscription.setInitialDate(initialDateTime);
        subscription.setEndSubscriptionDate(endDateTime);
        subscription.setRenewed(false);
        subscription.setActive(true);
        subscription.setSubscriberID(resource.getUsername());
        return dbRepository.save(subscription);
    }
    @Override
    public Subscription switchPlan(String username, String plan, String authorization,boolean internal) throws Exception {
        Optional<Subscription> subscriptionDB = dbRepository.findBySubscriberID(username);

        if (subscriptionDB.isPresent()) {
            Subscription subscription = subscriptionDB.get();

            subscription.setPlan(plan);
            return dbRepository.save(subscription);
        }


        if (!internal) {
            Subscription sub = httpRepository.swichtPlan(plan, authorization);

            if (sub != null) {
                return sub;
            }
        }

        throw new NotFoundException(Subscription.class, username);
    }
    @Override
    public Subscription cancel(String username, String authorization, boolean internal) throws Exception {

        Optional<Subscription> subscriptionDB = dbRepository.findBySubscriberID(username);

        if (subscriptionDB.isPresent()) {
            Subscription sub = subscriptionDB.get();

            if (!sub.getStatus()) {
                throw new InconsistencyDataException(Subscription.class,username,"Can't cancel something that was already cancel");
            }

            LocalDateTime date = LocalDateTime.now();

            sub.setFinalDate(date);
            sub.deactivate();

            return dbRepository.save(sub);
        }

        if (!internal) {
            final var sub = httpRepository.cancelSubscription(authorization);

            if (sub != null) {
                return sub;
            }
        }
        throw new NotFoundException(Subscription.class,username);
    }
    @Override
    public Subscription renewSubscription(String username, String authorization, boolean internal) throws Exception{

        Optional<Subscription> subscriptionDB = dbRepository.findBySubscriberID(username);

        if (subscriptionDB.isPresent()) {
            Subscription subscription = subscriptionDB.get();
            if (subscription.getFeeType() == FeeType.NA) {
                return null;
            }
            LocalDateTime currentEndDate = subscription.getEndSubscriptionDate();
            LocalDateTime newEndDate = null;
            if (subscription.getFeeType() == FeeType.MONTHLY) {
                newEndDate = currentEndDate.plusMonths(1);
            } else if (subscription.getFeeType() == FeeType.ANNUAL) {
                newEndDate = currentEndDate.plusYears(1);
            }
            subscription.setEndSubscriptionDate(newEndDate);

            subscription.setRenewed(true);

            return dbRepository.save(subscription);
        }

        if (!internal) {
            final var sub = httpRepository.renewSubscription(authorization);

            if (sub != null) {
                return sub;
            }
        }

        throw new NotFoundException(Subscription.class, username);
    }
    @Override
    public String getDetailsByUsername(String username, String authorization, boolean internal) throws Exception {

        Optional<Subscription> subscriptionDB = dbRepository.findBySubscriberID(username);
        String response;

        if (subscriptionDB.isPresent()) {
            String planName = subscriptionDB.get().getPlan();
            response = driver.checkIfPlanExistsResponse(planName);

            return response;
        }

        if (!internal) {
            response = httpRepository.getDetailsByUsername(authorization);

            if (response != null) {
                return response;
            }
        }

        throw new NotFoundException("Plan");

    }
}
