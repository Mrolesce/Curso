package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.entities.EntityBase;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category extends EntityBase<Category> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	private int categoryId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@PastOrPresent
	private Timestamp lastUpdate;

	@Column(nullable=false, length=25)
	@NotBlank
	@Size(max=25, min=2)
	private String name;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="category")
	private List<FilmCategory> filmCategories;

	public Category() {
	}

	public Category(int categoryId, @NotBlank @Size(max = 25, min = 2) String name, List<FilmCategory> filmCategories) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.filmCategories = filmCategories;
	}

	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

}