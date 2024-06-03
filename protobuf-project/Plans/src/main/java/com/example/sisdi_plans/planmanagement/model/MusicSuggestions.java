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

	public static MusicSuggestions fromInt(int value) {
		switch (value){
			case 0:
				return MusicSuggestions.AUTOMATIC;
			case 1:
				return MusicSuggestions.PERSONALIZED;
			default:
				return null;
		}
	}

	public static int toInt(MusicSuggestions musicSuggestions) {
		switch (musicSuggestions){
			case AUTOMATIC:
				return 0;
			case PERSONALIZED:
				return 1;
			default:
				return -12;
		}
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
