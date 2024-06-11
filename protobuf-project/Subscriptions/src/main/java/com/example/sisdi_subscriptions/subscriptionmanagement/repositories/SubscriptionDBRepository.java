package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDBRepository extends CrudRepository<SubscriptionJPA,Long>  {

	Optional<SubscriptionJPA> findById (Long id);
	@Query("SELECT s FROM SubscriptionJPA s WHERE s.subscriberID = :subscriberID")
	Optional<SubscriptionJPA> findBySubscriberID(String subscriberID);


	@Query("SELECT s FROM SubscriptionJPA s WHERE s.plan = :planName")
	List<SubscriptionJPA> findByPlan(String planName);

	//List<Subscription> findByPlanId(long oldPlanId);
}
