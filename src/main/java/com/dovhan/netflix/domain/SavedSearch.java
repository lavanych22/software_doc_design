package com.dovhan.netflix.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SavedSearch.
 */
@Entity
@Table(name = "saved_search")
public class SavedSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userID;

    @Size(max = 500)
    @Column(name = "search", length = 500)
    private String search;

    @ManyToOne
    @JsonIgnoreProperties("savedSearches")
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

    public SavedSearch userID(Long userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getSearch() {
        return search;
    }

    public SavedSearch search(String search) {
        this.search = search;
        return this;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public NetflixUser getNetflixUser() {
        return netflixUser;
    }

    public SavedSearch netflixUser(NetflixUser netflixUser) {
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
        if (!(o instanceof SavedSearch)) {
            return false;
        }
        return id != null && id.equals(((SavedSearch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SavedSearch{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", search='" + getSearch() + "'" +
            "}";
    }
}
