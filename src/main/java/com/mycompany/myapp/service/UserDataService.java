package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserData.
 */
public interface UserDataService {

    /**
     * Save a userData.
     *
     * @param userData the entity to save
     * @return the persisted entity
     */
    UserData save(UserData userData);

    /**
     * Get all the userData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserData> findAll(Pageable pageable);


    /**
     * Get the "id" userData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserData> findOne(Long id);

    /**
     * Delete the "id" userData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
