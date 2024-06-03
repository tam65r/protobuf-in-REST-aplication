package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDBRepository extends CrudRepository<Subscription,Long>  {

	Optional<Subscription> findById (Long id);
	@Query("SELECT s FROM Subscription s WHERE s.subscriberID = :subscriberID")
	Optional<Subscription> findBySubscriberID(String subscriberID);


	List<Subscription> findByPlan(Optional<String> planName);

	//List<Subscription> findByPlanId(long oldPlanId);
}
