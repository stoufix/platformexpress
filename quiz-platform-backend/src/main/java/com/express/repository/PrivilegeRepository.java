package com.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.Privilege;

/**
 * Represents repository of privilege
 * 

 * @version 1.0
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
