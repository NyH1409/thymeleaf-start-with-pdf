package com.api.app.cnaps.mapper;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Employee;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class CnapsMapper {
    public CnapsEmployee toCnapsEmployee(Employee employee) {
        return CnapsEmployee.builder()
                .id(randomUUID().toString())
                .matriculate(employee.getMatriculate())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .sex(employee.getSex())
                .birthDate(employee.getBirthDate())
                .emailPerso(employee.getEmailPerso())
                .emailPro(employee.getEmailPro())
                .entranceDate(employee.getEntranceDate())
                .leavingDate(employee.getLeavingDate())
                .category(employee.getCategory())
                .children(employee.getChildren())
                .endToEndId(employee.getId())
                .build();
    }

    public Employee toDomain(Employee employee, CnapsEmployee cnapsEmployee) {
        return employee.toBuilder()
                .cnaps(cnapsEmployee != null ? cnapsEmployee.getCnaps() : null)
                .build();
    }

}
