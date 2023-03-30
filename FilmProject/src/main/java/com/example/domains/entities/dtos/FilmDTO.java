package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;

public class FilmDTO {
	
	private int filmId;

	private String description;

	private Timestamp lastUpdate;
	
	private int length;

	private String rating;

	private int releaseYear;

	private int rentalDuration;

	private BigDecimal rentalRate;

	private BigDecimal replacementCost;

	private String title;
	
}
