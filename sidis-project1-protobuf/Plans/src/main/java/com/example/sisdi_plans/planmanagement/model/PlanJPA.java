package com.example.sisdi_plans.planmanagement.model;


import com.example.sisdi_plans.exceptions.InconsistencyDataException;

import javax.persistence.*;

import javax.validation.constraints.*;

@Entity
@Table(name = "plans")
public class PlanJPA {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true, updatable = false)
	@NotNull
	@NotBlank
	@Size(min = 1, max = 32)
	private String name;

	@Column(nullable = false)
	@Size(min = 0, max = 2048)
	private String description;

	@Column(nullable = false)
	@Min(0)
	private int numberOfMinutes;

	@Column(nullable = true)
	@Min(1) @Max(1)
	private int maxNumberDevices;

	@Column(nullable = false)
	private MusicSuggestions musicSuggestions;

	@Column(nullable = false)
	@Min(0)
	private int musicCollections;

	@Column(nullable = false)
	@Min(0)
	private float monthlyFee;

	@Column(nullable = false)
	@Min(0)
	private float annualFee;

	@Column(nullable = false)
	private boolean isActive;

	public PlanJPA(){}

	public PlanJPA(String name, String description, int numberOfMinutes, MusicSuggestions musicSuggestions, int musicCollections, float monthlyFee, float annualFee){
		setName(name);
		this.description = description;
		this.musicCollections = musicCollections;
		this.musicSuggestions = musicSuggestions;
		this.numberOfMinutes = numberOfMinutes;
		this.annualFee = annualFee;
		this.monthlyFee = monthlyFee;
		this.maxNumberDevices = 1;
		this.isActive = true;
	}

	private void setName(final String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("'name' is a mandatory attribute of Plan");
		}
		if (!name.matches("^[a-zA-Z0-9_-]+$")) {
			throw new IllegalArgumentException("Invalid chracter(s) in 'name', i.e., only alphanumeric are valid");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Long getId(){return this.id;}

	public boolean getIsActive() {
		return this.isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfMinutes() {
		return numberOfMinutes;
	}

	public void setNumberOfMinutes(int numberOfMinutes) {
		this.numberOfMinutes = numberOfMinutes;
	}

	public int getMaxNumberDevices() {
		return maxNumberDevices;
	}

	public void setMaxNumberDevices(int maxNumberDevices) {
		this.maxNumberDevices = maxNumberDevices;
	}

	public MusicSuggestions getMusicSuggestions() {
		return musicSuggestions;
	}

	public void setMusicSuggestions(MusicSuggestions musicSuggestions) {
		this.musicSuggestions = musicSuggestions;
	}

	public int getMusicCollections() {
		return musicCollections;
	}

	public void setMusicCollections(int musicCollections) {
		this.musicCollections = musicCollections;
	}

	public float getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(float monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public float getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(float annualFee) {
		this.annualFee = annualFee;
	}

	public void setActive(boolean isActive) {this.isActive = isActive;}

	public void changeActivityStatus() {
		if (!this.isActive) {
			throw new InconsistencyDataException(PlanJPA.class, this.name,"Plan is already inactive");
		}
		this.isActive = false;
	}

	public void applyPatch(String description, String numberOfMinutes, String musicCollections, String musicSuggestions) {

		if (description != null) {
			setDescription(description);
		}

		if (musicSuggestions != null) {
			setMusicSuggestions(MusicSuggestions.fromString(musicSuggestions));
		}

		if (musicCollections != null) {
			try {
				setMusicCollections(Integer.parseInt(musicCollections));
			}catch (NumberFormatException e) {
				throw new InconsistencyDataException(PlanJPA.class, this.name, "Invalid number of minutes");
			}
		}

		if (numberOfMinutes != null) {
			try {
				setNumberOfMinutes(Integer.parseInt(numberOfMinutes));
			} catch (NumberFormatException e) {
				throw new InconsistencyDataException(PlanJPA.class, this.name, "Invalid number of minutes");
			}

		}

	}
}


