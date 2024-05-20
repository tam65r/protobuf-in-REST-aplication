package com.example.sisdi_subscriptions.subscriptionmanagement.model;

public enum FeeType {
	ANNUAL("Annual"),
	MONTHLY("Monthly"),
	NA("N/A");
	private final String description;

	FeeType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static FeeType fromString(String text) {
		if (text != null) {
			for (FeeType feeType : FeeType.values()) {
				if (text.equalsIgnoreCase(feeType.description)) {
					return feeType;
				}
			}
		}
		throw new IllegalArgumentException("Invalid FeeType: " + text);
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
