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
	public static FeeType fromInt(int value) {
		switch (value){
			case 0:
				return FeeType.NA;
			case 1:
				return FeeType.MONTHLY;
			case 2:
				return FeeType.ANNUAL;
			default:
				return null;
		}
	}

	public static int toInt(FeeType feeType) {
		switch (feeType){
			case NA:
				return 0;
			case MONTHLY:
				return 1;
			case ANNUAL:
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
