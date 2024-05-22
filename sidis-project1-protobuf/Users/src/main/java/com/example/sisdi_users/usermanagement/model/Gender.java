package com.example.sisdi_users.usermanagement.model;

public enum Gender {
	FEMALE("Female"),
	MALE("Male"),
	OTHER("Other");

	private final String description;

	Gender(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static Gender fromString(String text) {
		if (text != null) {
			for (Gender gender : Gender.values()) {
				if (text.equalsIgnoreCase(gender.description)) {
					return gender;
				}
			}
		}
		throw new IllegalArgumentException("Invalid Gender: " + text);
	}

	public static Gender fromInt(int value) {
		switch (value){
			case 0:
				return Gender.OTHER;
			case 1:
				return Gender.FEMALE;
			case 2:
				return Gender.MALE;
			default:
				return null;
		}
	}

	public static int toInt(Gender gender) {
		switch (gender){
			case OTHER:
				return 0;
			case FEMALE:
				return 1;
			case MALE:
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
