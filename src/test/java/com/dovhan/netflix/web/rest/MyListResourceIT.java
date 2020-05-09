package com.dovhan.netflix.web.rest;

import com.dovhan.netflix.NetflixApp;
import com.dovhan.netflix.domain.MyList;
import com.dovhan.netflix.repository.MyListRepository;
import com.dovhan.netflix.service.MyListService;

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
 * Integration tests for the {@link MyListResource} REST controller.
 */
@SpringBootTest(classes = NetflixApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MyListResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private MyListRepository myListRepository;

    @Autowired
    private MyListService myListService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyListMockMvc;

    private MyList myList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyList createEntity(EntityManager em) {
        MyList myList = new MyList()
            .userID(DEFAULT_USER_ID);
        return myList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyList createUpdatedEntity(EntityManager em) {
        MyList myList = new MyList()
            .userID(UPDATED_USER_ID);
        return myList;
    }

    @BeforeEach
    public void initTest() {
        myList = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyList() throws Exception {
        int databaseSizeBeforeCreate = myListRepository.findAll().size();

        // Create the MyList
        restMyListMockMvc.perform(post("/api/my-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myList)))
            .andExpect(status().isCreated());

        // Validate the MyList in the database
        List<MyList> myListList = myListRepository.findAll();
        assertThat(myListList).hasSize(databaseSizeBeforeCreate + 1);
        MyList testMyList = myListList.get(myListList.size() - 1);
        assertThat(testMyList.getUserID()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createMyListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myListRepository.findAll().size();

        // Create the MyList with an existing ID
        myList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyListMockMvc.perform(post("/api/my-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myList)))
            .andExpect(status().isBadRequest());

        // Validate the MyList in the database
        List<MyList> myListList = myListRepository.findAll();
        assertThat(myListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMyLists() throws Exception {
        // Initialize the database
        myListRepository.saveAndFlush(myList);

        // Get all the myListList
        restMyListMockMvc.perform(get("/api/my-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myList.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getMyList() throws Exception {
        // Initialize the database
        myListRepository.saveAndFlush(myList);

        // Get the myList
        restMyListMockMvc.perform(get("/api/my-lists/{id}", myList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myList.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMyList() throws Exception {
        // Get the myList
        restMyListMockMvc.perform(get("/api/my-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyList() throws Exception {
        // Initialize the database
        myListService.save(myList);

        int databaseSizeBeforeUpdate = myListRepository.findAll().size();

        // Update the myList
        MyList updatedMyList = myListRepository.findById(myList.getId()).get();
        // Disconnect from session so that the updates on updatedMyList are not directly saved in db
        em.detach(updatedMyList);
        updatedMyList
            .userID(UPDATED_USER_ID);

        restMyListMockMvc.perform(put("/api/my-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyList)))
            .andExpect(status().isOk());

        // Validate the MyList in the database
        List<MyList> myListList = myListRepository.findAll();
        assertThat(myListList).hasSize(databaseSizeBeforeUpdate);
        MyList testMyList = myListList.get(myListList.size() - 1);
        assertThat(testMyList.getUserID()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingMyList() throws Exception {
        int databaseSizeBeforeUpdate = myListRepository.findAll().size();

        // Create the MyList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyListMockMvc.perform(put("/api/my-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myList)))
            .andExpect(status().isBadRequest());

        // Validate the MyList in the database
        List<MyList> myListList = myListRepository.findAll();
        assertThat(myListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyList() throws Exception {
        // Initialize the database
        myListService.save(myList);

        int databaseSizeBeforeDelete = myListRepository.findAll().size();

        // Delete the myList
        restMyListMockMvc.perform(delete("/api/my-lists/{id}", myList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyList> myListList = myListRepository.findAll();
        assertThat(myListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
