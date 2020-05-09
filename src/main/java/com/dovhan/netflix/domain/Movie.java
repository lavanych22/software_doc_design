package com.dovhan.netflix.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.dovhan.netflix.domain.enumeration.Type;

import com.dovhan.netflix.domain.enumeration.Genre;

import com.dovhan.netflix.domain.enumeration.AvailableInHD;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "release_date")
    private ZonedDateTime releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @NotNull
    @Size(max = 100)
    @Column(name = "creator", length = 100, nullable = false)
    private String creator;

    @NotNull
    @Size(max = 200)
    @Column(name = "cast", length = 200, nullable = false)
    private String cast;

    @Column(name = "rating")
    private Float rating;

    @NotNull
    @Size(max = 1000)
    @Column(name = "link", length = 1000, nullable = false)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "available_in_hd")
    private AvailableInHD availableInHD;

    @OneToMany(mappedBy = "movie")
    private Set<Episode> episodes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("movies")
    private MyList myList;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Movie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Movie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getReleaseDate() {
        return releaseDate;
    }

    public Movie releaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Type getType() {
        return type;
    }

    public Movie type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Genre getGenre() {
        return genre;
    }

    public Movie genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getCreator() {
        return creator;
    }

    public Movie creator(String creator) {
        this.creator = creator;
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCast() {
        return cast;
    }

    public Movie cast(String cast) {
        this.cast = cast;
        return this;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Float getRating() {
        return rating;
    }

    public Movie rating(Float rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getLink() {
        return link;
    }

    public Movie link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AvailableInHD getAvailableInHD() {
        return availableInHD;
    }

    public Movie availableInHD(AvailableInHD availableInHD) {
        this.availableInHD = availableInHD;
        return this;
    }

    public void setAvailableInHD(AvailableInHD availableInHD) {
        this.availableInHD = availableInHD;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public Movie episodes(Set<Episode> episodes) {
        this.episodes = episodes;
        return this;
    }

    public Movie addEpisode(Episode episode) {
        this.episodes.add(episode);
        episode.setMovie(this);
        return this;
    }

    public Movie removeEpisode(Episode episode) {
        this.episodes.remove(episode);
        episode.setMovie(null);
        return this;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public MyList getMyList() {
        return myList;
    }

    public Movie myList(MyList myList) {
        this.myList = myList;
        return this;
    }

    public void setMyList(MyList myList) {
        this.myList = myList;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", type='" + getType() + "'" +
            ", genre='" + getGenre() + "'" +
            ", creator='" + getCreator() + "'" +
            ", cast='" + getCast() + "'" +
            ", rating=" + getRating() +
            ", link='" + getLink() + "'" +
            ", availableInHD='" + getAvailableInHD() + "'" +
            "}";
    }
}
