package com.api.app.service;

import com.api.app.model.Employee;
import com.api.app.model.exception.ApiException;
import com.api.app.repository.Repository;
import com.api.app.repository.dao.EmployeeDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeDao employeeDao;
    private final Repository mainRepository;

    public List<Employee> getEmployees(
      String firstName,
      String lastName,
      String sex,
      String job,
      String code,
      String entranceDatetime,
      String leavingDatetime,
      String firstNameOrder,
      String lastNameOrder,
      String sexOrder,
      String jobOrder,
      String codeOrder,
      Integer page,
      Integer pageSize) {
        int pageValue = page == null ? 0 : page;
        int pageSizeValue = pageSize == null ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageValue, pageSizeValue);
        return employeeDao.findByCriteria(firstName, lastName, sex, job, code, entranceDatetime, leavingDatetime, firstNameOrder,
                lastNameOrder, sexOrder, jobOrder, codeOrder, pageable);
    }

    public Employee getEmployee(String id) {
        return mainRepository.getEmployeeById(id);
    }

    public Employee getEmployeeByEmail(String username) {
        return mainRepository.findByPrincipalUsername(username).orElse(null);
    }

    public Employee crupdateEmployee(Employee employee) {
        return mainRepository.save(employee);
    }

    public void generateCSV(List<Employee> employees, PrintWriter printWriter) {
        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(printWriter, CsvPreference.STANDARD_PREFERENCE);
            List<String> heardersAndMapping = Arrays.stream(Employee.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());
            String[] stringArray = new String[heardersAndMapping.size()];
            csvWriter.writeHeader(heardersAndMapping.toArray(stringArray));
            for (Employee employee : employees) {
                csvWriter.write(employee, heardersAndMapping.toArray(stringArray));
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }
}
