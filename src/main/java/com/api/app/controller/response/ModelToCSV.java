package com.api.app.controller.response;

import com.api.app.model.Employee;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ModelToCSV {
    private List<Employee> employees;
}
