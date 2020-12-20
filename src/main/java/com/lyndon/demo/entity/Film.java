package com.lyndon.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "film")
@Data
@NoArgsConstructor
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "release_year")
	private String releaseYear;

	@Column(name = "rental_duration")
	private Integer rentalDuration;

	@Column(name = "rental_rate")
	private Float rentalRate;

	@Column(name = "length")
	private Integer length;

	@Column(name = "replacement_cost")
	private Float replacementCost;

	@Column(name = "rating")
	private String rating;

	@Column(name = "special_features")
	private String specialFeatures;

	@Column(name = "last_update")
	private Date lastUpdate;

	@ManyToOne
	@JoinColumn(name = "language_id")
	private Language language;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "original_language_id")
	private Language originalLanguage;

	@ManyToMany
	@JoinTable(name = "film_actor", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Actor> actors;

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Film) {
			Film film = (Film) obj;
			return id.equals(film.id);
		}

		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(id).toString();
	}

}
