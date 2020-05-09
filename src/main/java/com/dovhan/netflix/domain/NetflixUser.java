package com.dovhan.netflix.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.dovhan.netflix.domain.enumeration.Category;

/**
 * A NetflixUser.
 */
@Entity
@Table(name = "netflix_user")
public class NetflixUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 1000)
    @Column(name = "bio", length = 1000)
    private String bio;

    @NotNull
    @Size(max = 50)
    @Column(name = "password", length = 50, nullable = false, unique = true)
    private String password;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @OneToOne
    @JoinColumn(unique = true)
    private MyList myList;

    @OneToMany(mappedBy = "netflixUser")
    private Set<SavedSearch> savedSearches = new HashSet<>();

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

    public NetflixUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public NetflixUser bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public NetflixUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public NetflixUser birthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Category getCategory() {
        return category;
    }

    public NetflixUser category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MyList getMyList() {
        return myList;
    }

    public NetflixUser myList(MyList myList) {
        this.myList = myList;
        return this;
    }

    public void setMyList(MyList myList) {
        this.myList = myList;
    }

    public Set<SavedSearch> getSavedSearches() {
        return savedSearches;
    }

    public NetflixUser savedSearches(Set<SavedSearch> savedSearches) {
        this.savedSearches = savedSearches;
        return this;
    }

    public NetflixUser addSavedSearch(SavedSearch savedSearch) {
        this.savedSearches.add(savedSearch);
        savedSearch.setNetflixUser(this);
        return this;
    }

    public NetflixUser removeSavedSearch(SavedSearch savedSearch) {
        this.savedSearches.remove(savedSearch);
        savedSearch.setNetflixUser(null);
        return this;
    }

    public void setSavedSearches(Set<SavedSearch> savedSearches) {
        this.savedSearches = savedSearches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetflixUser)) {
            return false;
        }
        return id != null && id.equals(((NetflixUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NetflixUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bio='" + getBio() + "'" +
            ", password='" + getPassword() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
