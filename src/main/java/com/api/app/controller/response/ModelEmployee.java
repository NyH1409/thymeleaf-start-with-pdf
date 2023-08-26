package com.api.app.controller.response;

import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.model.Principal;
import com.api.app.model.Identity;
import com.api.app.model.Job;
import com.api.app.model.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class ModelEmployee {
    private String id;
    private String matriculate;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;
    private MultipartFile image;
    private String base64Image;
    private String sex;
    private PhoneNumber phoneNumber;
    private String phoneWithCode;
    private String emailPerso;
    private String emailPro;
    private Principal principal;
    private Identity nic;
    private Job job;
    private Long children;
    private String category;
    private String cnaps;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate entrance;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate left;
    private Double salary;
    private int age;
}
