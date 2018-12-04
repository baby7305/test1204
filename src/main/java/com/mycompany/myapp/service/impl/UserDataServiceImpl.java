package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserDataService;
import com.mycompany.myapp.domain.UserData;
import com.mycompany.myapp.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserData.
 */
@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

    private final Logger log = LoggerFactory.getLogger(UserDataServiceImpl.class);

    private final UserDataRepository userDataRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    /**
     * Save a userData.
     *
     * @param userData the entity to save
     * @return the persisted entity
     */
    @Override
    public UserData save(UserData userData) {
        log.debug("Request to save UserData : {}", userData);
        return userDataRepository.save(userData);
    }

    /**
     * Get all the userData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserData> findAll(Pageable pageable) {
        log.debug("Request to get all UserData");
        return userDataRepository.findAll(pageable);
    }


    /**
     * Get one userData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserData> findOne(Long id) {
        log.debug("Request to get UserData : {}", id);
        return userDataRepository.findById(id);
    }

    /**
     * Delete the userData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserData : {}", id);
        userDataRepository.deleteById(id);
    }
}
