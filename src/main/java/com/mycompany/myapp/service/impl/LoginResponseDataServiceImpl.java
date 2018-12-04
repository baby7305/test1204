package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.LoginResponseDataService;
import com.mycompany.myapp.domain.LoginResponseData;
import com.mycompany.myapp.repository.LoginResponseDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LoginResponseData.
 */
@Service
@Transactional
public class LoginResponseDataServiceImpl implements LoginResponseDataService {

    private final Logger log = LoggerFactory.getLogger(LoginResponseDataServiceImpl.class);

    private final LoginResponseDataRepository loginResponseDataRepository;

    public LoginResponseDataServiceImpl(LoginResponseDataRepository loginResponseDataRepository) {
        this.loginResponseDataRepository = loginResponseDataRepository;
    }

    /**
     * Save a loginResponseData.
     *
     * @param loginResponseData the entity to save
     * @return the persisted entity
     */
    @Override
    public LoginResponseData save(LoginResponseData loginResponseData) {
        log.debug("Request to save LoginResponseData : {}", loginResponseData);
        return loginResponseDataRepository.save(loginResponseData);
    }

    /**
     * Get all the loginResponseData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoginResponseData> findAll(Pageable pageable) {
        log.debug("Request to get all LoginResponseData");
        return loginResponseDataRepository.findAll(pageable);
    }


    /**
     * Get one loginResponseData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LoginResponseData> findOne(Long id) {
        log.debug("Request to get LoginResponseData : {}", id);
        return loginResponseDataRepository.findById(id);
    }

    /**
     * Delete the loginResponseData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginResponseData : {}", id);
        loginResponseDataRepository.deleteById(id);
    }
}
