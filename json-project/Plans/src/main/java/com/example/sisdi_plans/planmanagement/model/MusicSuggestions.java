package com.example.sisdi_plans.planmanagement.model;

public enum MusicSuggestions {
	AUTOMATIC("Automatic"),
	PERSONALIZED("Personalized");

	private final String description;

	MusicSuggestions(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static MusicSuggestions fromString(String text) {
		if (text != null) {
			for (MusicSuggestions musicSuggestions : MusicSuggestions.values()) {
				if (text.equalsIgnoreCase(musicSuggestions.description)) {
					return musicSuggestions;
				}
			}
		}
		throw new IllegalArgumentException("Invalid Music Suggestions: " + text);
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
