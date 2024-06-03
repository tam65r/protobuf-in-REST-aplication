package com.example.sisdi_subscriptions.subscriptionmanagement.model;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String subscriberID;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 1, max = 32)
	private String plan;

	@Nullable
	@Column(nullable = false)
	private LocalDateTime initialDate;


	private boolean isActive;

	@Nullable
	private LocalDateTime finalDate;

	private FeeType feeType;

	private PaymentMethod paymentMethod;

	private boolean isRenewed;
	@Nullable
	private LocalDateTime endSubscriptionDate; // esta é minha ass Hugo

	public Subscription(){
		this.isActive = true;
		this.subscriberID = null;
	}

	public Subscription(String subscriberID, String plan, LocalDateTime initialDate, FeeType feeType, PaymentMethod paymentMethod, LocalDateTime endSubscriptionDate) {
		this.subscriberID = subscriberID;
		this.plan = plan;
		this.initialDate = initialDate;
		this.feeType = feeType;
		this.paymentMethod = paymentMethod;
		this.isActive = true;
		this.isRenewed = false;
		this.endSubscriptionDate = endSubscriptionDate;
	}

	public Long getId() {
		return id;
	}

	public boolean getStatus(){return this.isActive;}
	public void setId(Long id) {
		this.id = id;
	}

	public void setRenewed(boolean renewed) {
		this.isRenewed = renewed;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}



	public String getSubscriberID() {
		return subscriberID;
	}

	public LocalDateTime getEndSubscriptionDate(){return endSubscriptionDate;}
	public void setEndSubscriptionDate(LocalDateTime endSubscriptionDate) {
		this.endSubscriptionDate = endSubscriptionDate;
	}

	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public LocalDateTime getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDateTime initialDate) {
		this.initialDate = initialDate;
	}


	public void deactivate() {
		isActive = false;
	}

	public LocalDateTime getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(LocalDateTime finalDate) {
		this.finalDate = finalDate;
	}


	public FeeType getFeeType() {
		return feeType;
	}

	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


}
