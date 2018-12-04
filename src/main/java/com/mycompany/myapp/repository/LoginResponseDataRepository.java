package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LoginResponseData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoginResponseData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginResponseDataRepository extends JpaRepository<LoginResponseData, Long> {

}
