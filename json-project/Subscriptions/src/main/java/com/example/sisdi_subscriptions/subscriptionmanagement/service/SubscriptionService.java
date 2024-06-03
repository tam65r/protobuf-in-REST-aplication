package com.example.sisdi_subscriptions.subscriptionmanagement.service;


import com.example.sisdi_subscriptions.subscriptionmanagement.api.CreateSubscriptionRequest;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

	final SubscriptionRepository subscriptionRepository;
	final PlanRepository planRepository;

	public Subscription create(CreateSubscriptionRequest resource) throws Exception {
		planRepository.checkIfPlanExists(resource.getPlan());
		return subscriptionRepository.create(resource);
	}


	public Subscription switchPlan(String username, String plan,  String authorization, boolean internal) throws Exception {
		planRepository.checkIfPlanExists(plan);
		if (!internal) {
			return subscriptionRepository.switchPlan(username,plan,authorization,false);

		} else {
			return subscriptionRepository.switchPlan(username,plan,null,true);
		}
	}

	public Subscription cancel(String username,  String authorization, boolean internal) throws Exception {
		if (!internal) {
			return subscriptionRepository.cancel(username,authorization,false);

		} else {
			return subscriptionRepository.cancel(username,null,true);
		}
	}
	public Subscription renewSubscription(String username,  String authorization, boolean internal) throws Exception{
		if (!internal) {
			return subscriptionRepository.renewSubscription(username,authorization,false);

		} else {
			return subscriptionRepository.renewSubscription(username,null,true);
		}
	}

	public String getDetailsByUsername(String username, String authorization, boolean internal) throws Exception {
		if (!internal) {
			return subscriptionRepository.getDetailsByUsername(username,authorization,false);
		} else {
			return subscriptionRepository.getDetailsByUsername(username,null,true);
		}
	}

}
