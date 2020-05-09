package com.dovhan.netflix.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A MyList.
 */
@Entity
@Table(name = "my_list")
public class MyList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userID;

    @OneToMany(mappedBy = "myList")
    private Set<Movie> movies = new HashSet<>();

    @OneToOne(mappedBy = "myList")
    @JsonIgnore
    private NetflixUser netflixUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public MyList userID(Long userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public MyList movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public MyList addMovie(Movie movie) {
        this.movies.add(movie);
        movie.setMyList(this);
        return this;
    }

    public MyList removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.setMyList(null);
        return this;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public NetflixUser getNetflixUser() {
        return netflixUser;
    }

    public MyList netflixUser(NetflixUser netflixUser) {
        this.netflixUser = netflixUser;
        return this;
    }

    public void setNetflixUser(NetflixUser netflixUser) {
        this.netflixUser = netflixUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyList)) {
            return false;
        }
        return id != null && id.equals(((MyList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MyList{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            "}";
    }
}
