package com.api.app.model.mapper;

import com.api.app.cnaps.repository.CnapsRepository;
import com.api.app.controller.response.ModelEmployee;
import com.api.app.model.Employee;
import com.api.app.model.PhoneNumber;
import com.api.app.model.Principal;
import com.api.app.model.exception.ApiException;
import com.api.app.model.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static java.util.UUID.randomUUID;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();
    private final CnapsRepository cnapsRepository;

    public Employee toDomain(ModelEmployee employee) {
        PhoneNumber phoneNumber = employee.getPhoneNumber();
        String phoneWithCode = phoneNumber != null ? phoneNumber
          .getCode() + " " + phoneNumber
          .getPhoneNumber()
          .substring(1) : null;

        MultipartFile multipartFile = employee.getImage();
        try {
            String encodedImage = multipartFile != null ? base64Encoder.encodeToString(employee.getImage().getBytes()) : employee.getBase64Image();
            String encodedPassword = base64Encoder.encodeToString(employee.getPrincipal().getPassword().getBytes());
            return Employee.builder()
                    .id(employee.getId() != null ? employee.getId() : randomUUID().toString())
                    .matriculate(employee.getMatriculate())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .sex(employee.getSex())
                    .birthDate(employee.getBirthDate())
                    .job(employee.getJob())
                    .image(encodedImage)
                    .principal(employee.getPrincipal().toBuilder()
                            .password(encodedPassword)
                            .build())
                    .nic(employee.getNic())
                    .emailPerso(employee.getEmailPerso())
                    .emailPro(employee.getEmailPro())
                    .entranceDate(employee.getEntrance())
                    .leavingDate(employee.getLeft())
                    .phoneNumbers(List.of(phoneNumber.toBuilder()
                            .phoneNumberWithCode(phoneWithCode)
                            .build()))
                    .category(categoryFromString(employee.getCategory()))
                    .children(employee.getChildren())
                    .cnaps(employee.getCnaps())
                    .build();
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

    public ModelEmployee toView(Employee employee) {
        Principal principal = employee.getPrincipal();
        byte[] decodedPass = base64Decoder.decode(principal.getPassword());
        return ModelEmployee.builder()
                .id(employee.getId() != null ? employee.getId() : randomUUID().toString())
                .matriculate(employee.getMatriculate())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .sex(employee.getSex())
                .birthDate(employee.getBirthDate())
                .principal(principal.toBuilder()
                        .password(new String(decodedPass, StandardCharsets.UTF_8))
                        .build())
                .base64Image(employee.getImage())
                .nic(employee.getNic())
                .emailPerso(employee.getEmailPerso())
                .emailPro(employee.getEmailPro())
                .entrance(employee.getEntranceDate())
                .left(employee.getLeavingDate())
                .phoneNumber(employee.getPhoneNumbers().get(0))
                .phoneWithCode(employee.getPhoneNumbers().get(0).getPhoneNumberWithCode())
                .category(employee.getCategory().toString())
                .children(employee.getChildren())
                .cnaps(employee.getCnaps())
          .job(employee.getJob())
          .build();
    }

    private Employee.Category categoryFromString(String category) {
        switch (category) {
            case "M1":
                return Employee.Category.M1;
            case "M2":
                return Employee.Category.M2;
            case "OS1":
                return Employee.Category.OS1;
            case "OS2":
                return Employee.Category.OS2;
            case "OS3":
                return Employee.Category.OS3;
            case "OP1":
                return Employee.Category.OP1;
            default:
                throw new BadRequestException("Invalid category");
        }
    }
}
