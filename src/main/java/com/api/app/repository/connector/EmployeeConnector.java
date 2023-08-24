package com.api.app.repository.connector;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Employee;

import java.util.Optional;

public interface EmployeeConnector {
    Optional<CnapsEmployee> findByEndToEndId(String id);

    Employee save(Employee employee);

    Optional<Employee> findById(String id);

    Optional<Employee> findByPrincipalUsername(String username);
}