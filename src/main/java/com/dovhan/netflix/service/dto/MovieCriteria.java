package com.dovhan.netflix.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.dovhan.netflix.domain.enumeration.Type;
import com.dovhan.netflix.domain.enumeration.Genre;
import com.dovhan.netflix.domain.enumeration.AvailableInHD;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.dovhan.netflix.domain.Movie} entity. This class is used
 * in {@link com.dovhan.netflix.web.rest.MovieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /movies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MovieCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Type
     */
    public static class TypeFilter extends Filter<Type> {

        public TypeFilter() {
        }

        public TypeFilter(TypeFilter filter) {
            super(filter);
        }

        @Override
        public TypeFilter copy() {
            return new TypeFilter(this);
        }

    }
    /**
     * Class for filtering Genre
     */
    public static class GenreFilter extends Filter<Genre> {

        public GenreFilter() {
        }

        public GenreFilter(GenreFilter filter) {
            super(filter);
        }

        @Override
        public GenreFilter copy() {
            return new GenreFilter(this);
        }

    }
    /**
     * Class for filtering AvailableInHD
     */
    public static class AvailableInHDFilter extends Filter<AvailableInHD> {

        public AvailableInHDFilter() {
        }

        public AvailableInHDFilter(AvailableInHDFilter filter) {
            super(filter);
        }

        @Override
        public AvailableInHDFilter copy() {
            return new AvailableInHDFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private ZonedDateTimeFilter releaseDate;

    private TypeFilter type;

    private GenreFilter genre;

    private StringFilter creator;

    private StringFilter cast;

    private FloatFilter rating;

    private StringFilter link;

    private AvailableInHDFilter availableInHD;

    private LongFilter episodeId;

    private LongFilter myListId;

    public MovieCriteria() {
    }

    public MovieCriteria(MovieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.releaseDate = other.releaseDate == null ? null : other.releaseDate.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.genre = other.genre == null ? null : other.genre.copy();
        this.creator = other.creator == null ? null : other.creator.copy();
        this.cast = other.cast == null ? null : other.cast.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.availableInHD = other.availableInHD == null ? null : other.availableInHD.copy();
        this.episodeId = other.episodeId == null ? null : other.episodeId.copy();
        this.myListId = other.myListId == null ? null : other.myListId.copy();
    }

    @Override
    public MovieCriteria copy() {
        return new MovieCriteria(this);
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

    public ZonedDateTimeFilter getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(ZonedDateTimeFilter releaseDate) {
        this.releaseDate = releaseDate;
    }

    public TypeFilter getType() {
        return type;
    }

    public void setType(TypeFilter type) {
        this.type = type;
    }

    public GenreFilter getGenre() {
        return genre;
    }

    public void setGenre(GenreFilter genre) {
        this.genre = genre;
    }

    public StringFilter getCreator() {
        return creator;
    }

    public void setCreator(StringFilter creator) {
        this.creator = creator;
    }

    public StringFilter getCast() {
        return cast;
    }

    public void setCast(StringFilter cast) {
        this.cast = cast;
    }

    public FloatFilter getRating() {
        return rating;
    }

    public void setRating(FloatFilter rating) {
        this.rating = rating;
    }

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public AvailableInHDFilter getAvailableInHD() {
        return availableInHD;
    }

    public void setAvailableInHD(AvailableInHDFilter availableInHD) {
        this.availableInHD = availableInHD;
    }

    public LongFilter getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(LongFilter episodeId) {
        this.episodeId = episodeId;
    }

    public LongFilter getMyListId() {
        return myListId;
    }

    public void setMyListId(LongFilter myListId) {
        this.myListId = myListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MovieCriteria that = (MovieCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(type, that.type) &&
            Objects.equals(genre, that.genre) &&
            Objects.equals(creator, that.creator) &&
            Objects.equals(cast, that.cast) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(link, that.link) &&
            Objects.equals(availableInHD, that.availableInHD) &&
            Objects.equals(episodeId, that.episodeId) &&
            Objects.equals(myListId, that.myListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        releaseDate,
        type,
        genre,
        creator,
        cast,
        rating,
        link,
        availableInHD,
        episodeId,
        myListId
        );
    }

    @Override
    public String toString() {
        return "MovieCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (releaseDate != null ? "releaseDate=" + releaseDate + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (genre != null ? "genre=" + genre + ", " : "") +
                (creator != null ? "creator=" + creator + ", " : "") +
                (cast != null ? "cast=" + cast + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (availableInHD != null ? "availableInHD=" + availableInHD + ", " : "") +
                (episodeId != null ? "episodeId=" + episodeId + ", " : "") +
                (myListId != null ? "myListId=" + myListId + ", " : "") +
            "}";
    }

}
