package com.api.app.cnaps.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class CnapsEmployee implements Serializable {
    @Id
    private String id;
    private String matriculate;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;
    @Type(type = "text")
    private String image;
    private String sex;
    private String emailPerso;
    private String emailPro;
    private Long children;
    @Enumerated(EnumType.STRING)
    private com.api.app.model.Employee.Category category;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate entranceDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate leavingDate;
    private String endToEndId;
    private String cnaps;

    @PrePersist
    public void generateCustomMatriculate() {
        if (matriculate == null) {
            matriculate = String.format("MAT-CNAPS-EMPLOYEE-%s", Instant.now().toEpochMilli());
        }
    }
}
