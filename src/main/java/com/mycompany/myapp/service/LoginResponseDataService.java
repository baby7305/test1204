package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.LoginResponseData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LoginResponseData.
 */
public interface LoginResponseDataService {

    /**
     * Save a loginResponseData.
     *
     * @param loginResponseData the entity to save
     * @return the persisted entity
     */
    LoginResponseData save(LoginResponseData loginResponseData);

    /**
     * Get all the loginResponseData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LoginResponseData> findAll(Pageable pageable);


    /**
     * Get the "id" loginResponseData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LoginResponseData> findOne(Long id);

    /**
     * Delete the "id" loginResponseData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
