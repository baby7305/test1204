package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the UserData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    List<UserData> findByUsername(String username);

}
