package com.api.app.repository;

import com.api.app.model.Employee;

import java.util.Optional;

public interface Repository {
    Employee save(Employee employee);
    Employee getEmployeeById(String id);
    Optional<Employee> findByPrincipalUsername(String username);
}
