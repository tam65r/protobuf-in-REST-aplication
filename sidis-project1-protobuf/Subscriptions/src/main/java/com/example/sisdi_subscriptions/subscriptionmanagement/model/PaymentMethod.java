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


	public static PaymentMethod fromInt(int value) {
		switch (value){
			case 0:
				return PaymentMethod.NA;
			case 1:
				return PaymentMethod.MBWAY;
			case 2:
				return PaymentMethod.CARD;
			default:
				return null;
		}
	}

	public static int toInt(PaymentMethod paymentMethod) {
		switch (paymentMethod){
			case NA:
				return 0;
			case MBWAY:
				return 1;
			case CARD:
				return 2;
			default:
				return -12;
		}
	}
	@Override
	public String toString() {
		return getDescription();
	}
}
