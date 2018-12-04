package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Test1204App;

import com.mycompany.myapp.domain.UserData;
import com.mycompany.myapp.repository.UserDataRepository;
import com.mycompany.myapp.service.UserDataService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserDataResource REST controller.
 *
 * @see UserDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test1204App.class)
public class UserDataResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDataMockMvc;

    private UserData userData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserDataResource userDataResource = new UserDataResource(userDataService);
        this.restUserDataMockMvc = MockMvcBuilders.standaloneSetup(userDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserData createEntity(EntityManager em) {
        UserData userData = new UserData()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD);
        return userData;
    }

    @Before
    public void initTest() {
        userData = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserData() throws Exception {
        int databaseSizeBeforeCreate = userDataRepository.findAll().size();

        // Create the UserData
        restUserDataMockMvc.perform(post("/api/user-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isCreated());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeCreate + 1);
        UserData testUserData = userDataList.get(userDataList.size() - 1);
        assertThat(testUserData.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserData.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createUserDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDataRepository.findAll().size();

        // Create the UserData with an existing ID
        userData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDataMockMvc.perform(post("/api/user-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        // Get all the userDataList
        restUserDataMockMvc.perform(get("/api/user-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userData.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    @Test
    @Transactional
    public void getUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        // Get the userData
        restUserDataMockMvc.perform(get("/api/user-data/{id}", userData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userData.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserData() throws Exception {
        // Get the userData
        restUserDataMockMvc.perform(get("/api/user-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserData() throws Exception {
        // Initialize the database
        userDataService.save(userData);

        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();

        // Update the userData
        UserData updatedUserData = userDataRepository.findById(userData.getId()).get();
        // Disconnect from session so that the updates on updatedUserData are not directly saved in db
        em.detach(updatedUserData);
        updatedUserData
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);

        restUserDataMockMvc.perform(put("/api/user-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserData)))
            .andExpect(status().isOk());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
        UserData testUserData = userDataList.get(userDataList.size() - 1);
        assertThat(testUserData.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserData.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();

        // Create the UserData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDataMockMvc.perform(put("/api/user-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserData() throws Exception {
        // Initialize the database
        userDataService.save(userData);

        int databaseSizeBeforeDelete = userDataRepository.findAll().size();

        // Get the userData
        restUserDataMockMvc.perform(delete("/api/user-data/{id}", userData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserData.class);
        UserData userData1 = new UserData();
        userData1.setId(1L);
        UserData userData2 = new UserData();
        userData2.setId(userData1.getId());
        assertThat(userData1).isEqualTo(userData2);
        userData2.setId(2L);
        assertThat(userData1).isNotEqualTo(userData2);
        userData1.setId(null);
        assertThat(userData1).isNotEqualTo(userData2);
    }
}
