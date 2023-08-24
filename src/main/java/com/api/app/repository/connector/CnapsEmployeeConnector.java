package com.api.app.repository.connector;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.cnaps.repository.CnapsRepository;
import com.api.app.model.Employee;
import com.api.app.model.exception.NotImplementedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class CnapsEmployeeConnector implements EmployeeConnector {
    private final CnapsRepository cnapsRepository;

    @Override
    public Optional<CnapsEmployee> findByEndToEndId(String id) {
        return cnapsRepository.findByEndToEndId(id);
    }

    @Override
    public Employee save(Employee employee) {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public Optional<Employee> findById(String id) {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public Optional<Employee> findByPrincipalUsername(String username) {
        throw new NotImplementedException("Not implemented");
    }
}