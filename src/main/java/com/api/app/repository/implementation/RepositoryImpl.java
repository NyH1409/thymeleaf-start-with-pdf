package com.api.app.repository.implementation;

import com.api.app.cnaps.mapper.CnapsMapper;
import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Employee;
import com.api.app.model.exception.NotFoundException;
import com.api.app.repository.Repository;
import com.api.app.repository.facade.EmployeeFacade;
import lombok.AllArgsConstructor;

import java.util.Optional;


@org.springframework.stereotype.Repository
@AllArgsConstructor
public class RepositoryImpl implements Repository {
    private final EmployeeFacade employeeFacade;
    private final CnapsMapper cnapsMapper;

    @Override
    public Employee save(Employee employee) {
        CnapsEmployee cnapsEmployee = employeeFacade.findByEndToEndId(employee.getId()).orElse(null);
        if (cnapsEmployee != null){
            return employeeFacade.save(employee);
        }
        return employeeFacade.save(cnapsMapper.toDomain(employee, cnapsEmployee));
    }

    @Override
    public Employee getEmployeeById(String id) {
        CnapsEmployee cnapsEmployee = employeeFacade.findByEndToEndId(id).orElse(null);
        Employee employee = employeeFacade.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        if (cnapsEmployee != null) {
            return employeeFacade.save(cnapsMapper.toDomain(employee, cnapsEmployee));
        }
        return employee;
    }

    @Override
    public Optional<Employee> findByPrincipalUsername(String username) {
        return employeeFacade.findByPrincipalUsername(username);
    }
}