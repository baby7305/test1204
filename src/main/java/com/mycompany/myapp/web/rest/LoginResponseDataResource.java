package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.LoginResponseData;
import com.mycompany.myapp.service.LoginResponseDataService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LoginResponseData.
 */
@RestController
@RequestMapping("/api")
public class LoginResponseDataResource {

    private final Logger log = LoggerFactory.getLogger(LoginResponseDataResource.class);

    private static final String ENTITY_NAME = "loginResponseData";

    private final LoginResponseDataService loginResponseDataService;

    public LoginResponseDataResource(LoginResponseDataService loginResponseDataService) {
        this.loginResponseDataService = loginResponseDataService;
    }

    /**
     * POST  /login-response-data : Create a new loginResponseData.
     *
     * @param loginResponseData the loginResponseData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loginResponseData, or with status 400 (Bad Request) if the loginResponseData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/login-response-data")
    @Timed
    public ResponseEntity<LoginResponseData> createLoginResponseData(@RequestBody LoginResponseData loginResponseData) throws URISyntaxException {
        log.debug("REST request to save LoginResponseData : {}", loginResponseData);
        if (loginResponseData.getId() != null) {
            throw new BadRequestAlertException("A new loginResponseData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginResponseData result = loginResponseDataService.save(loginResponseData);
        return ResponseEntity.created(new URI("/api/login-response-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /login-response-data : Updates an existing loginResponseData.
     *
     * @param loginResponseData the loginResponseData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loginResponseData,
     * or with status 400 (Bad Request) if the loginResponseData is not valid,
     * or with status 500 (Internal Server Error) if the loginResponseData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/login-response-data")
    @Timed
    public ResponseEntity<LoginResponseData> updateLoginResponseData(@RequestBody LoginResponseData loginResponseData) throws URISyntaxException {
        log.debug("REST request to update LoginResponseData : {}", loginResponseData);
        if (loginResponseData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoginResponseData result = loginResponseDataService.save(loginResponseData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loginResponseData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /login-response-data : get all the loginResponseData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loginResponseData in body
     */
    @GetMapping("/login-response-data")
    @Timed
    public ResponseEntity<List<LoginResponseData>> getAllLoginResponseData(Pageable pageable) {
        log.debug("REST request to get a page of LoginResponseData");
        Page<LoginResponseData> page = loginResponseDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/login-response-data");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /login-response-data/:id : get the "id" loginResponseData.
     *
     * @param id the id of the loginResponseData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loginResponseData, or with status 404 (Not Found)
     */
    @GetMapping("/login-response-data/{id}")
    @Timed
    public ResponseEntity<LoginResponseData> getLoginResponseData(@PathVariable Long id) {
        log.debug("REST request to get LoginResponseData : {}", id);
        Optional<LoginResponseData> loginResponseData = loginResponseDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loginResponseData);
    }

    /**
     * DELETE  /login-response-data/:id : delete the "id" loginResponseData.
     *
     * @param id the id of the loginResponseData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/login-response-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoginResponseData(@PathVariable Long id) {
        log.debug("REST request to delete LoginResponseData : {}", id);
        loginResponseDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
