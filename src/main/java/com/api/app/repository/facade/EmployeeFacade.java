package com.api.app.repository.facade;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Employee;
import com.api.app.repository.connector.BaseEmployeeConnector;
import com.api.app.repository.connector.CnapsEmployeeConnector;
import com.api.app.repository.connector.EmployeeConnector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class EmployeeFacade implements EmployeeConnector {
    private final CnapsEmployeeConnector cnapsEmployeeConnector;
    private final BaseEmployeeConnector baseEmployeeConnector;

    @Override
    public Optional<CnapsEmployee> findByEndToEndId(String id) {
        return cnapsEmployeeConnector.findByEndToEndId(id);
    }

    @Override
    public Employee save(Employee employee) {
        return baseEmployeeConnector.save(employee);
    }

    @Override
    public Optional<Employee> findById(String id) {
        return baseEmployeeConnector.findById(id);
    }

    @Override
    public Optional<Employee> findByPrincipalUsername(String username) {
        return baseEmployeeConnector.findByPrincipalUsername(username);
    }
}