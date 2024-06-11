package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;


import com.example.sisdi_subscriptions.exceptions.DuplicatedDataException;
import com.example.sisdi_subscriptions.exceptions.InconsistencyDataException;
import com.example.sisdi_subscriptions.exceptions.NotFoundException;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.FeeType;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.PaymentMethod;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;
import com.example.sisdi_subscriptions.subscriptionmanagement.service.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.sisdi_subscriptions.subscriptionmanagement.api.proto.SubscriptionRequests.CreateSubscriptionRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SubscriberRepositoryImpl implements SubscriptionRepository {

    final SubscriptionDBRepository dbRepository;
    final SubscriptionHTTPRepository httpRepository;
    final PlanRepositoryImpl driver;

    @Override
    public SubscriptionJPA create(CreateSubscriptionRequest resource) throws Exception {
        String plan = resource.getPlan();
        SubscriptionJPA subscriptionJPA = new SubscriptionJPA();

        if (dbRepository.findBySubscriberID(resource.getUsername()).isPresent()) {
            throw new DuplicatedDataException(SubscriptionJPA.class,resource.getUsername());
        }

        if (plan.equals("Free")) {
            subscriptionJPA.setPlan("Free");
            subscriptionJPA.setFeeType(FeeType.NA);
            subscriptionJPA.setPaymentMethod(PaymentMethod.NA);
        } else {
            subscriptionJPA.setPlan(plan);
            if (resource.getFeeType() == null || resource.getPaymentMethod() == null || resource.getPaymentMethod().isEmpty() || resource.getFeeType().isEmpty()) {
                throw new InconsistencyDataException(SubscriptionJPA.class,"Null not a valid value");
            }
            if (resource.getFeeType().equals(FeeType.NA.toString()) || resource.getPaymentMethod().equals(PaymentMethod.NA.toString())) {
                throw new InconsistencyDataException(SubscriptionJPA.class,"Null not a valid value");
            }

            subscriptionJPA.setFeeType(FeeType.fromString(resource.getFeeType()));
            subscriptionJPA.setPaymentMethod(PaymentMethod.fromString(resource.getPaymentMethod()));
        }

        LocalDateTime initialDateTime = LocalDateTime.now();

        LocalDateTime endDateTime = null;

        if (subscriptionJPA.getFeeType() == FeeType.MONTHLY) {
            endDateTime = initialDateTime.plusMonths(1);
        } else if (subscriptionJPA.getFeeType() == FeeType.ANNUAL) {
            endDateTime = initialDateTime.plusYears(1);
        }


        subscriptionJPA.setInitialDate(initialDateTime);
        subscriptionJPA.setEndSubscriptionDate(endDateTime);
        subscriptionJPA.setRenewed(false);
        subscriptionJPA.setActive(true);
        subscriptionJPA.setSubscriberID(resource.getUsername());
        return dbRepository.save(subscriptionJPA);
    }
    @Override
    public SubscriptionJPA switchPlan(String username, String plan, String authorization, boolean internal) throws Exception {
        Optional<SubscriptionJPA> subscriptionDB = dbRepository.findBySubscriberID(username);

        if (subscriptionDB.isPresent()) {
            SubscriptionJPA subscriptionJPA = subscriptionDB.get();

            subscriptionJPA.setPlan(plan);
            return dbRepository.save(subscriptionJPA);
        }


        if (!internal) {
            SubscriptionJPA sub = httpRepository.swichtPlan(plan, authorization);

            if (sub != null) {
                return sub;
            }
        }

        throw new NotFoundException(SubscriptionJPA.class, username);
    }
    @Override
    public SubscriptionJPA cancel(String username, String authorization, boolean internal) throws Exception {

        Optional<SubscriptionJPA> subscriptionDB = dbRepository.findBySubscriberID(username);

        if (subscriptionDB.isPresent()) {
            SubscriptionJPA sub = subscriptionDB.get();

            if (!sub.getStatus()) {
                throw new InconsistencyDataException(SubscriptionJPA.class,username,"Can't cancel something that was already cancel");
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
        throw new NotFoundException(SubscriptionJPA.class,username);
    }
    @Override
    public SubscriptionJPA renewSubscription(String username, String authorization, boolean internal) throws Exception{

        Optional<SubscriptionJPA> subscriptionDB = dbRepository.findBySubscriberID(username);

        if (subscriptionDB.isPresent()) {
            SubscriptionJPA subscriptionJPA = subscriptionDB.get();
            if (subscriptionJPA.getFeeType() == FeeType.NA) {
                return null;
            }
            LocalDateTime currentEndDate = subscriptionJPA.getEndSubscriptionDate();
            LocalDateTime newEndDate = null;
            if (subscriptionJPA.getFeeType() == FeeType.MONTHLY) {
                newEndDate = currentEndDate.plusMonths(1);
            } else if (subscriptionJPA.getFeeType() == FeeType.ANNUAL) {
                newEndDate = currentEndDate.plusYears(1);
            }
            subscriptionJPA.setEndSubscriptionDate(newEndDate);

            subscriptionJPA.setRenewed(true);

            return dbRepository.save(subscriptionJPA);
        }

        if (!internal) {
            final var sub = httpRepository.renewSubscription(authorization);

            if (sub != null) {
                return sub;
            }
        }

        throw new NotFoundException(SubscriptionJPA.class, username);
    }
    @Override
    public byte [] getDetailsByUsername(String username, String authorization, boolean internal) throws Exception {

        Optional<SubscriptionJPA> subscriptionDB = dbRepository.findBySubscriberID(username);
        byte [] response;

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

    @Override
    public List<SubscriptionJPA> subscriptionDetailsByPlan(String plan, String authorization, boolean internal) throws Exception {

        List<SubscriptionJPA> subscriptions = dbRepository.findByPlan(plan);

        if (internal) {
            return subscriptions;
        }

        List<SubscriptionJPA> list = httpRepository.subscriptionByPlan(plan, authorization);

        if (list != null) {
            subscriptions.addAll(list);
        }

        return subscriptions;
    }

}
