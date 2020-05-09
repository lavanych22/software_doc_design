package com.dovhan.netflix.web.rest;

import com.dovhan.netflix.NetflixApp;
import com.dovhan.netflix.domain.SavedSearch;
import com.dovhan.netflix.repository.SavedSearchRepository;
import com.dovhan.netflix.service.SavedSearchService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SavedSearchResource} REST controller.
 */
@SpringBootTest(classes = NetflixApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SavedSearchResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_SEARCH = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH = "BBBBBBBBBB";

    @Autowired
    private SavedSearchRepository savedSearchRepository;

    @Autowired
    private SavedSearchService savedSearchService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSavedSearchMockMvc;

    private SavedSearch savedSearch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedSearch createEntity(EntityManager em) {
        SavedSearch savedSearch = new SavedSearch()
            .userID(DEFAULT_USER_ID)
            .search(DEFAULT_SEARCH);
        return savedSearch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedSearch createUpdatedEntity(EntityManager em) {
        SavedSearch savedSearch = new SavedSearch()
            .userID(UPDATED_USER_ID)
            .search(UPDATED_SEARCH);
        return savedSearch;
    }

    @BeforeEach
    public void initTest() {
        savedSearch = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedSearch() throws Exception {
        int databaseSizeBeforeCreate = savedSearchRepository.findAll().size();

        // Create the SavedSearch
        restSavedSearchMockMvc.perform(post("/api/saved-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedSearch)))
            .andExpect(status().isCreated());

        // Validate the SavedSearch in the database
        List<SavedSearch> savedSearchList = savedSearchRepository.findAll();
        assertThat(savedSearchList).hasSize(databaseSizeBeforeCreate + 1);
        SavedSearch testSavedSearch = savedSearchList.get(savedSearchList.size() - 1);
        assertThat(testSavedSearch.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSavedSearch.getSearch()).isEqualTo(DEFAULT_SEARCH);
    }

    @Test
    @Transactional
    public void createSavedSearchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = savedSearchRepository.findAll().size();

        // Create the SavedSearch with an existing ID
        savedSearch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSavedSearchMockMvc.perform(post("/api/saved-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedSearch)))
            .andExpect(status().isBadRequest());

        // Validate the SavedSearch in the database
        List<SavedSearch> savedSearchList = savedSearchRepository.findAll();
        assertThat(savedSearchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSavedSearches() throws Exception {
        // Initialize the database
        savedSearchRepository.saveAndFlush(savedSearch);

        // Get all the savedSearchList
        restSavedSearchMockMvc.perform(get("/api/saved-searches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(savedSearch.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].search").value(hasItem(DEFAULT_SEARCH)));
    }
    
    @Test
    @Transactional
    public void getSavedSearch() throws Exception {
        // Initialize the database
        savedSearchRepository.saveAndFlush(savedSearch);

        // Get the savedSearch
        restSavedSearchMockMvc.perform(get("/api/saved-searches/{id}", savedSearch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(savedSearch.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.search").value(DEFAULT_SEARCH));
    }

    @Test
    @Transactional
    public void getNonExistingSavedSearch() throws Exception {
        // Get the savedSearch
        restSavedSearchMockMvc.perform(get("/api/saved-searches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedSearch() throws Exception {
        // Initialize the database
        savedSearchService.save(savedSearch);

        int databaseSizeBeforeUpdate = savedSearchRepository.findAll().size();

        // Update the savedSearch
        SavedSearch updatedSavedSearch = savedSearchRepository.findById(savedSearch.getId()).get();
        // Disconnect from session so that the updates on updatedSavedSearch are not directly saved in db
        em.detach(updatedSavedSearch);
        updatedSavedSearch
            .userID(UPDATED_USER_ID)
            .search(UPDATED_SEARCH);

        restSavedSearchMockMvc.perform(put("/api/saved-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSavedSearch)))
            .andExpect(status().isOk());

        // Validate the SavedSearch in the database
        List<SavedSearch> savedSearchList = savedSearchRepository.findAll();
        assertThat(savedSearchList).hasSize(databaseSizeBeforeUpdate);
        SavedSearch testSavedSearch = savedSearchList.get(savedSearchList.size() - 1);
        assertThat(testSavedSearch.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSavedSearch.getSearch()).isEqualTo(UPDATED_SEARCH);
    }

    @Test
    @Transactional
    public void updateNonExistingSavedSearch() throws Exception {
        int databaseSizeBeforeUpdate = savedSearchRepository.findAll().size();

        // Create the SavedSearch

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSavedSearchMockMvc.perform(put("/api/saved-searches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedSearch)))
            .andExpect(status().isBadRequest());

        // Validate the SavedSearch in the database
        List<SavedSearch> savedSearchList = savedSearchRepository.findAll();
        assertThat(savedSearchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSavedSearch() throws Exception {
        // Initialize the database
        savedSearchService.save(savedSearch);

        int databaseSizeBeforeDelete = savedSearchRepository.findAll().size();

        // Delete the savedSearch
        restSavedSearchMockMvc.perform(delete("/api/saved-searches/{id}", savedSearch.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SavedSearch> savedSearchList = savedSearchRepository.findAll();
        assertThat(savedSearchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
