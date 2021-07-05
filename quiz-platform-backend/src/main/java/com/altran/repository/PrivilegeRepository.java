package com.altran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.model.Privilege;

/**
 * Represents repository of privilege
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
