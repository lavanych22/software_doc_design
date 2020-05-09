package com.dovhan.netflix.service;

import com.dovhan.netflix.domain.SavedSearch;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SavedSearch}.
 */
public interface SavedSearchService {

    /**
     * Save a savedSearch.
     *
     * @param savedSearch the entity to save.
     * @return the persisted entity.
     */
    SavedSearch save(SavedSearch savedSearch);

    /**
     * Get all the savedSearches.
     *
     * @return the list of entities.
     */
    List<SavedSearch> findAll();

    /**
     * Get the "id" savedSearch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SavedSearch> findOne(Long id);

    /**
     * Delete the "id" savedSearch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
