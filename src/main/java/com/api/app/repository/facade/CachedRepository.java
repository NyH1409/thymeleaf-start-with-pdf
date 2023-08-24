package com.api.app.repository.facade;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Employee;
import com.api.app.repository.connector.EmployeeConnector;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CachedRepository implements EmployeeConnector {
    private final EmployeeConnector connector;

    @Override
    public Optional<CnapsEmployee> findByEndToEndId(String id) {
        return connector.findByEndToEndId(id);
    }

    @Override
    public Employee save(Employee employee) {
        return connector.save(employee);
    }

    @Override
    public Optional<Employee> findById(String id) {
        return connector.findById(id);
    }

    @Override
    public Optional<Employee> findByPrincipalUsername(String username) {
        return connector.findByPrincipalUsername(username);
    }
}