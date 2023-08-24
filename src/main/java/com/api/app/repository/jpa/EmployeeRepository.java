package com.api.app.repository.jpa;

import com.api.app.model.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByPrincipalUsername(String username);

}
