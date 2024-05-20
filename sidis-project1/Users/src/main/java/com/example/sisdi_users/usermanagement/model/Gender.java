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

	@Override
	public String toString() {
		return getDescription();
	}
}
