package com.example.sisdi_plans.utils;

import java.time.LocalDateTime;


public class Utils {

	public static LocalDateTime parseString(String stringDate) {
		String[] parts = stringDate.split("-");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Invalid date format");
		}
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);


		return LocalDateTime.of(year,month,day,0,0);
	}


}
