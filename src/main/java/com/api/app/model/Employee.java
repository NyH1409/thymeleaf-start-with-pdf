package com.api.app.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Employee implements Serializable {
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
  @OneToMany(cascade = CascadeType.ALL)
  private List<PhoneNumber> phoneNumbers;
  private String emailPerso;
  private String emailPro;
  @OneToOne(cascade = CascadeType.ALL)
  private Identity nic;
  @OneToOne(cascade = CascadeType.ALL)
  private Job job;
  private Long children;
  @Enumerated(EnumType.STRING)
  private Category category;
  @OneToOne(cascade = CascadeType.ALL)
  private Principal principal;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private LocalDate entranceDate;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private LocalDate leavingDate;
  private String cnaps;
  private Double salary;
  @Transient
  private int age;

  @PrePersist
  public void generateCustomMatriculate() {
    if (matriculate == null) {
      matriculate = String.format("MAT-EMPLOYEE-%s", Instant.now().toEpochMilli());
    }
  }

  public int getAge() {
    return Period.between(birthDate, LocalDate.now()).getYears();
  }

  public enum Category {
    M1, M2, OS1, OS2, OS3, OP1
  }
}
