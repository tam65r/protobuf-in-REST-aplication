package com.example.sisdi_subscriptions.subscriptionmanagement.model;

public enum PaymentMethod {
	CARD("Card"),
	MBWAY("MBWay"),
	NA("N/A");
	private final String description;

	PaymentMethod(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static PaymentMethod fromString(String text) {
		if (text != null) {
			for (PaymentMethod paymentMethod : PaymentMethod.values()) {
				if (text.equalsIgnoreCase(paymentMethod.description)) {
					return paymentMethod;
				}
			}
		}
		throw new IllegalArgumentException("Invalid PaymentMethod: " + text);
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
