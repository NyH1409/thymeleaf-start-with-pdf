package com.api.app.controller;

import com.api.app.controller.response.ModelEmployee;
import com.api.app.controller.response.ModelToCSV;
import com.api.app.controller.security.Provider;
import com.api.app.model.Company;
import com.api.app.model.Employee;
import com.api.app.model.exception.ForbiddenException;
import com.api.app.model.mapper.EmployeeMapper;
import com.api.app.service.CompanyService;
import com.api.app.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private final CompanyService companyService;
    private final Provider provider;

    @GetMapping("/employees/create")
    public String createPage(Model model) {
        try {
            model.addAttribute("employee", new ModelEmployee());
            List<Company> companies = companyService.getCompanies();
            model.addAttribute("company", companies.get(0));
            return "create";
        } catch (ForbiddenException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/")
    public String getEmployees(ModelMap model,
                               @RequestParam(value = "firstName", required = false) String fistName,
                               @RequestParam(value = "lastName", required = false) String lastName,
                               @RequestParam(value = "sex", required = false) String sex,
                               @RequestParam(value = "job", required = false) String job,
                               @RequestParam(value = "code", required = false) String code,
                               @RequestParam(value = "entrance", required = false) String entranceDatetime,
                               @RequestParam(value = "leaving", required = false) String leavingDatetime,
                               @RequestParam(value = "firstNameOrder", required = false) String firstNameOrder,
                               @RequestParam(value = "lastNameOrder", required = false) String lastNameOrder,
                               @RequestParam(value = "sexOrder", required = false) String sexOrder,
                               @RequestParam(value = "jobOrder", required = false) String jobOrder,
                               @RequestParam(value = "codeOrder", required = false) String codeOrder,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        try {
            provider.isAuthenticated();
            List<Employee> employees = service.getEmployees(
              fistName, lastName, sex, job, code, entranceDatetime, leavingDatetime, firstNameOrder, lastNameOrder, sexOrder, jobOrder, codeOrder, page, pageSize);
            List<Company> companies = companyService.getCompanies();
            ModelToCSV modelToCSV = new ModelToCSV(employees);
            model.addAttribute("company", companies.get(0));
            model.addAttribute("employees", employees);
            model.addAttribute("modelToCSV", modelToCSV);
            return "index";
        } catch (ForbiddenException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/employees/{id}")
    public String getEmployee(ModelMap model, @PathVariable("id") String employeeId) {
        try {
            provider.isAuthenticated();
            Employee employee = service.getEmployee(employeeId);
            List<Company> companies = companyService.getCompanies();
            model.addAttribute("company", companies.get(0));
            model.addAttribute("employee", employee);
            return "profile";
        } catch (ForbiddenException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/employees/{id}/edit")
    public String updateEmployee(ModelMap model, @PathVariable("id") String employeeId) {
        try {
            provider.isAuthenticated();
            Employee employee = service.getEmployee(employeeId);
            ModelEmployee modelEmployee = mapper.toView(employee);
            List<Company> companies = companyService.getCompanies();
            model.addAttribute("company", companies.get(0));
            model.addAttribute("employee", employee);
            model.addAttribute("modelEmployee", modelEmployee);
            return "edit";
        } catch (ForbiddenException e) {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/employees")
    public RedirectView createEmployee(@ModelAttribute ModelEmployee employee) {
        try {
            provider.isAuthenticated();
            service.crupdateEmployee(mapper.toDomain(employee));
            return new RedirectView("/");
        } catch (ForbiddenException e) {
            return new RedirectView("/login");
        }
    }

    @PostMapping(value = "/employees/{id}")
    public RedirectView updateEmployee(@PathVariable("id") String id, @ModelAttribute ModelEmployee employee) {
        try {
            provider.isAuthenticated();
            service.crupdateEmployee(mapper.toDomain(employee.toBuilder().id(id).build()));
            return new RedirectView("/");
        } catch (ForbiddenException e) {
            return new RedirectView("/login");
        }
    }

    @PostMapping(value = "/employees/raw")
    public RedirectView generateCSV(@ModelAttribute ModelToCSV modelToCSV, HttpServletResponse response) throws IOException {
        try {
            provider.isAuthenticated();
            response.setContentType("text/csv");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime;
            response.setHeader(headerKey, headerValue);
            service.generateCSV(modelToCSV.getEmployees(), response.getWriter());
            return new RedirectView("/");
        } catch (ForbiddenException e) {
            return new RedirectView("/login");
        }
    }

}
