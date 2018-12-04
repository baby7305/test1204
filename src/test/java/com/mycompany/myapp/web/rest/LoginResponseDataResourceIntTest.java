package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Test1204App;

import com.mycompany.myapp.domain.LoginResponseData;
import com.mycompany.myapp.repository.LoginResponseDataRepository;
import com.mycompany.myapp.service.LoginResponseDataService;
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
 * Test class for the LoginResponseDataResource REST controller.
 *
 * @see LoginResponseDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test1204App.class)
public class LoginResponseDataResourceIntTest {

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final Boolean UPDATED_SUCCESS = true;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private LoginResponseDataRepository loginResponseDataRepository;

    @Autowired
    private LoginResponseDataService loginResponseDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLoginResponseDataMockMvc;

    private LoginResponseData loginResponseData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoginResponseDataResource loginResponseDataResource = new LoginResponseDataResource(loginResponseDataService);
        this.restLoginResponseDataMockMvc = MockMvcBuilders.standaloneSetup(loginResponseDataResource)
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
    public static LoginResponseData createEntity(EntityManager em) {
        LoginResponseData loginResponseData = new LoginResponseData()
            .success(DEFAULT_SUCCESS)
            .message(DEFAULT_MESSAGE);
        return loginResponseData;
    }

    @Before
    public void initTest() {
        loginResponseData = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoginResponseData() throws Exception {
        int databaseSizeBeforeCreate = loginResponseDataRepository.findAll().size();

        // Create the LoginResponseData
        restLoginResponseDataMockMvc.perform(post("/api/login-response-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginResponseData)))
            .andExpect(status().isCreated());

        // Validate the LoginResponseData in the database
        List<LoginResponseData> loginResponseDataList = loginResponseDataRepository.findAll();
        assertThat(loginResponseDataList).hasSize(databaseSizeBeforeCreate + 1);
        LoginResponseData testLoginResponseData = loginResponseDataList.get(loginResponseDataList.size() - 1);
        assertThat(testLoginResponseData.isSuccess()).isEqualTo(DEFAULT_SUCCESS);
        assertThat(testLoginResponseData.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createLoginResponseDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loginResponseDataRepository.findAll().size();

        // Create the LoginResponseData with an existing ID
        loginResponseData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginResponseDataMockMvc.perform(post("/api/login-response-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginResponseData)))
            .andExpect(status().isBadRequest());

        // Validate the LoginResponseData in the database
        List<LoginResponseData> loginResponseDataList = loginResponseDataRepository.findAll();
        assertThat(loginResponseDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLoginResponseData() throws Exception {
        // Initialize the database
        loginResponseDataRepository.saveAndFlush(loginResponseData);

        // Get all the loginResponseDataList
        restLoginResponseDataMockMvc.perform(get("/api/login-response-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginResponseData.getId().intValue())))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getLoginResponseData() throws Exception {
        // Initialize the database
        loginResponseDataRepository.saveAndFlush(loginResponseData);

        // Get the loginResponseData
        restLoginResponseDataMockMvc.perform(get("/api/login-response-data/{id}", loginResponseData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loginResponseData.getId().intValue()))
            .andExpect(jsonPath("$.success").value(DEFAULT_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoginResponseData() throws Exception {
        // Get the loginResponseData
        restLoginResponseDataMockMvc.perform(get("/api/login-response-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoginResponseData() throws Exception {
        // Initialize the database
        loginResponseDataService.save(loginResponseData);

        int databaseSizeBeforeUpdate = loginResponseDataRepository.findAll().size();

        // Update the loginResponseData
        LoginResponseData updatedLoginResponseData = loginResponseDataRepository.findById(loginResponseData.getId()).get();
        // Disconnect from session so that the updates on updatedLoginResponseData are not directly saved in db
        em.detach(updatedLoginResponseData);
        updatedLoginResponseData
            .success(UPDATED_SUCCESS)
            .message(UPDATED_MESSAGE);

        restLoginResponseDataMockMvc.perform(put("/api/login-response-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoginResponseData)))
            .andExpect(status().isOk());

        // Validate the LoginResponseData in the database
        List<LoginResponseData> loginResponseDataList = loginResponseDataRepository.findAll();
        assertThat(loginResponseDataList).hasSize(databaseSizeBeforeUpdate);
        LoginResponseData testLoginResponseData = loginResponseDataList.get(loginResponseDataList.size() - 1);
        assertThat(testLoginResponseData.isSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testLoginResponseData.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingLoginResponseData() throws Exception {
        int databaseSizeBeforeUpdate = loginResponseDataRepository.findAll().size();

        // Create the LoginResponseData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginResponseDataMockMvc.perform(put("/api/login-response-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginResponseData)))
            .andExpect(status().isBadRequest());

        // Validate the LoginResponseData in the database
        List<LoginResponseData> loginResponseDataList = loginResponseDataRepository.findAll();
        assertThat(loginResponseDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoginResponseData() throws Exception {
        // Initialize the database
        loginResponseDataService.save(loginResponseData);

        int databaseSizeBeforeDelete = loginResponseDataRepository.findAll().size();

        // Get the loginResponseData
        restLoginResponseDataMockMvc.perform(delete("/api/login-response-data/{id}", loginResponseData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LoginResponseData> loginResponseDataList = loginResponseDataRepository.findAll();
        assertThat(loginResponseDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginResponseData.class);
        LoginResponseData loginResponseData1 = new LoginResponseData();
        loginResponseData1.setId(1L);
        LoginResponseData loginResponseData2 = new LoginResponseData();
        loginResponseData2.setId(loginResponseData1.getId());
        assertThat(loginResponseData1).isEqualTo(loginResponseData2);
        loginResponseData2.setId(2L);
        assertThat(loginResponseData1).isNotEqualTo(loginResponseData2);
        loginResponseData1.setId(null);
        assertThat(loginResponseData1).isNotEqualTo(loginResponseData2);
    }
}
