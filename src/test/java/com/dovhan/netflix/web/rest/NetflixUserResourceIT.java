package com.dovhan.netflix.web.rest;

import com.dovhan.netflix.NetflixApp;
import com.dovhan.netflix.domain.NetflixUser;
import com.dovhan.netflix.repository.NetflixUserRepository;
import com.dovhan.netflix.service.NetflixUserService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.dovhan.netflix.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dovhan.netflix.domain.enumeration.Category;
/**
 * Integration tests for the {@link NetflixUserResource} REST controller.
 */
@SpringBootTest(classes = NetflixApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class NetflixUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Category DEFAULT_CATEGORY = Category.Child;
    private static final Category UPDATED_CATEGORY = Category.Adult;

    @Autowired
    private NetflixUserRepository netflixUserRepository;

    @Autowired
    private NetflixUserService netflixUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNetflixUserMockMvc;

    private NetflixUser netflixUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetflixUser createEntity(EntityManager em) {
        NetflixUser netflixUser = new NetflixUser()
            .name(DEFAULT_NAME)
            .bio(DEFAULT_BIO)
            .password(DEFAULT_PASSWORD)
            .birthDate(DEFAULT_BIRTH_DATE)
            .category(DEFAULT_CATEGORY);
        return netflixUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetflixUser createUpdatedEntity(EntityManager em) {
        NetflixUser netflixUser = new NetflixUser()
            .name(UPDATED_NAME)
            .bio(UPDATED_BIO)
            .password(UPDATED_PASSWORD)
            .birthDate(UPDATED_BIRTH_DATE)
            .category(UPDATED_CATEGORY);
        return netflixUser;
    }

    @BeforeEach
    public void initTest() {
        netflixUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetflixUser() throws Exception {
        int databaseSizeBeforeCreate = netflixUserRepository.findAll().size();

        // Create the NetflixUser
        restNetflixUserMockMvc.perform(post("/api/netflix-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(netflixUser)))
            .andExpect(status().isCreated());

        // Validate the NetflixUser in the database
        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeCreate + 1);
        NetflixUser testNetflixUser = netflixUserList.get(netflixUserList.size() - 1);
        assertThat(testNetflixUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNetflixUser.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testNetflixUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testNetflixUser.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testNetflixUser.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createNetflixUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = netflixUserRepository.findAll().size();

        // Create the NetflixUser with an existing ID
        netflixUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetflixUserMockMvc.perform(post("/api/netflix-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(netflixUser)))
            .andExpect(status().isBadRequest());

        // Validate the NetflixUser in the database
        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = netflixUserRepository.findAll().size();
        // set the field null
        netflixUser.setName(null);

        // Create the NetflixUser, which fails.

        restNetflixUserMockMvc.perform(post("/api/netflix-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(netflixUser)))
            .andExpect(status().isBadRequest());

        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = netflixUserRepository.findAll().size();
        // set the field null
        netflixUser.setPassword(null);

        // Create the NetflixUser, which fails.

        restNetflixUserMockMvc.perform(post("/api/netflix-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(netflixUser)))
            .andExpect(status().isBadRequest());

        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNetflixUsers() throws Exception {
        // Initialize the database
        netflixUserRepository.saveAndFlush(netflixUser);

        // Get all the netflixUserList
        restNetflixUserMockMvc.perform(get("/api/netflix-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netflixUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }
    
    @Test
    @Transactional
    public void getNetflixUser() throws Exception {
        // Initialize the database
        netflixUserRepository.saveAndFlush(netflixUser);

        // Get the netflixUser
        restNetflixUserMockMvc.perform(get("/api/netflix-users/{id}", netflixUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(netflixUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNetflixUser() throws Exception {
        // Get the netflixUser
        restNetflixUserMockMvc.perform(get("/api/netflix-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetflixUser() throws Exception {
        // Initialize the database
        netflixUserService.save(netflixUser);

        int databaseSizeBeforeUpdate = netflixUserRepository.findAll().size();

        // Update the netflixUser
        NetflixUser updatedNetflixUser = netflixUserRepository.findById(netflixUser.getId()).get();
        // Disconnect from session so that the updates on updatedNetflixUser are not directly saved in db
        em.detach(updatedNetflixUser);
        updatedNetflixUser
            .name(UPDATED_NAME)
            .bio(UPDATED_BIO)
            .password(UPDATED_PASSWORD)
            .birthDate(UPDATED_BIRTH_DATE)
            .category(UPDATED_CATEGORY);

        restNetflixUserMockMvc.perform(put("/api/netflix-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetflixUser)))
            .andExpect(status().isOk());

        // Validate the NetflixUser in the database
        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeUpdate);
        NetflixUser testNetflixUser = netflixUserList.get(netflixUserList.size() - 1);
        assertThat(testNetflixUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNetflixUser.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testNetflixUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testNetflixUser.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testNetflixUser.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingNetflixUser() throws Exception {
        int databaseSizeBeforeUpdate = netflixUserRepository.findAll().size();

        // Create the NetflixUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetflixUserMockMvc.perform(put("/api/netflix-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(netflixUser)))
            .andExpect(status().isBadRequest());

        // Validate the NetflixUser in the database
        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetflixUser() throws Exception {
        // Initialize the database
        netflixUserService.save(netflixUser);

        int databaseSizeBeforeDelete = netflixUserRepository.findAll().size();

        // Delete the netflixUser
        restNetflixUserMockMvc.perform(delete("/api/netflix-users/{id}", netflixUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetflixUser> netflixUserList = netflixUserRepository.findAll();
        assertThat(netflixUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
