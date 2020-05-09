package com.dovhan.netflix.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.dovhan.netflix.domain.Episode} entity. This class is used
 * in {@link com.dovhan.netflix.web.rest.EpisodeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /episodes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EpisodeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter producer;

    private ZonedDateTimeFilter releaseDate;

    private StringFilter videoURL;

    private LongFilter movieId;

    public EpisodeCriteria() {
    }

    public EpisodeCriteria(EpisodeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.producer = other.producer == null ? null : other.producer.copy();
        this.releaseDate = other.releaseDate == null ? null : other.releaseDate.copy();
        this.videoURL = other.videoURL == null ? null : other.videoURL.copy();
        this.movieId = other.movieId == null ? null : other.movieId.copy();
    }

    @Override
    public EpisodeCriteria copy() {
        return new EpisodeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getProducer() {
        return producer;
    }

    public void setProducer(StringFilter producer) {
        this.producer = producer;
    }

    public ZonedDateTimeFilter getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(ZonedDateTimeFilter releaseDate) {
        this.releaseDate = releaseDate;
    }

    public StringFilter getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(StringFilter videoURL) {
        this.videoURL = videoURL;
    }

    public LongFilter getMovieId() {
        return movieId;
    }

    public void setMovieId(LongFilter movieId) {
        this.movieId = movieId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EpisodeCriteria that = (EpisodeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(producer, that.producer) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(videoURL, that.videoURL) &&
            Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        producer,
        releaseDate,
        videoURL,
        movieId
        );
    }

    @Override
    public String toString() {
        return "EpisodeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (producer != null ? "producer=" + producer + ", " : "") +
                (releaseDate != null ? "releaseDate=" + releaseDate + ", " : "") +
                (videoURL != null ? "videoURL=" + videoURL + ", " : "") +
                (movieId != null ? "movieId=" + movieId + ", " : "") +
            "}";
    }

}
