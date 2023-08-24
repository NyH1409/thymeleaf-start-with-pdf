package com.api.app.repository.connector;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Employee;
import com.api.app.model.exception.NotImplementedException;
import com.api.app.repository.jpa.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
@Primary
public class BaseEmployeeConnector implements EmployeeConnector {
    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<CnapsEmployee> findByEndToEndId(String id) {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Employee> findByPrincipalUsername(String username) {
        return employeeRepository.findByPrincipalUsername(username);
    }

}