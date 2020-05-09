package com.dovhan.netflix.web.rest;

import com.dovhan.netflix.domain.SavedSearch;
import com.dovhan.netflix.service.SavedSearchService;
import com.dovhan.netflix.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dovhan.netflix.domain.SavedSearch}.
 */
@RestController
@RequestMapping("/api")
public class SavedSearchResource {

    private final Logger log = LoggerFactory.getLogger(SavedSearchResource.class);

    private static final String ENTITY_NAME = "savedSearch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SavedSearchService savedSearchService;

    public SavedSearchResource(SavedSearchService savedSearchService) {
        this.savedSearchService = savedSearchService;
    }

    /**
     * {@code POST  /saved-searches} : Create a new savedSearch.
     *
     * @param savedSearch the savedSearch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new savedSearch, or with status {@code 400 (Bad Request)} if the savedSearch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saved-searches")
    public ResponseEntity<SavedSearch> createSavedSearch(@Valid @RequestBody SavedSearch savedSearch) throws URISyntaxException {
        log.debug("REST request to save SavedSearch : {}", savedSearch);
        if (savedSearch.getId() != null) {
            throw new BadRequestAlertException("A new savedSearch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SavedSearch result = savedSearchService.save(savedSearch);
        return ResponseEntity.created(new URI("/api/saved-searches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saved-searches} : Updates an existing savedSearch.
     *
     * @param savedSearch the savedSearch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated savedSearch,
     * or with status {@code 400 (Bad Request)} if the savedSearch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the savedSearch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saved-searches")
    public ResponseEntity<SavedSearch> updateSavedSearch(@Valid @RequestBody SavedSearch savedSearch) throws URISyntaxException {
        log.debug("REST request to update SavedSearch : {}", savedSearch);
        if (savedSearch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SavedSearch result = savedSearchService.save(savedSearch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, savedSearch.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /saved-searches} : get all the savedSearches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of savedSearches in body.
     */
    @GetMapping("/saved-searches")
    public List<SavedSearch> getAllSavedSearches() {
        log.debug("REST request to get all SavedSearches");
        return savedSearchService.findAll();
    }

    /**
     * {@code GET  /saved-searches/:id} : get the "id" savedSearch.
     *
     * @param id the id of the savedSearch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the savedSearch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saved-searches/{id}")
    public ResponseEntity<SavedSearch> getSavedSearch(@PathVariable Long id) {
        log.debug("REST request to get SavedSearch : {}", id);
        Optional<SavedSearch> savedSearch = savedSearchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(savedSearch);
    }

    /**
     * {@code DELETE  /saved-searches/:id} : delete the "id" savedSearch.
     *
     * @param id the id of the savedSearch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saved-searches/{id}")
    public ResponseEntity<Void> deleteSavedSearch(@PathVariable Long id) {
        log.debug("REST request to delete SavedSearch : {}", id);
        savedSearchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
