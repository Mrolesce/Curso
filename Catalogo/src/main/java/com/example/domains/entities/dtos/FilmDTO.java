package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmDTO {
	private int filmId;
	private String description;
	private int length;
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	private byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	@NotBlank
	@Size(min=2, max = 128)
	private String title;
	@NotNull
	private String language;
	private String languageVO;
	private List<String> actors = new ArrayList<String>();
	private List<String> categories = new ArrayList<String>();
	//List<Map.Entry<Integer, String>> actores = new ArrayList<>();

	public static FilmDTO from(Film source) {
	    return new FilmDTO(
	            source.getFilmId(),
	            source.getDescription(),
	            source.getLength(),
	            source.getRating() == null ? null : source.getRating().getValue(),
	            source.getReleaseYear(),
	            source.getRentalDuration(),
	            source.getRentalRate(),
	            source.getReplacementCost(),
	            source.getTitle(),
	            source.getLanguage() == null ? null : source.getLanguage().getName(),
	            source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
	            source.getActors().stream().map(item -> item.getFirstName()+" "+item.getLastName())
	                    .collect(Collectors.toList()),
	            source.getCategories().stream().map(item -> item.getName())
	                    .collect(Collectors.toList())
	    );
	}

}