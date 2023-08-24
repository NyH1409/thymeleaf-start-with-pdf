package com.api.app.cnaps.repository;

import com.api.app.cnaps.model.CnapsEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CnapsRepository extends JpaRepository<CnapsEmployee, String> {
    Optional<CnapsEmployee> findByEndToEndId(String endToEndId);
}
