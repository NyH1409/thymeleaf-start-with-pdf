package com.api.app.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
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
  private String birthDate;
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
  private String entranceDate;
  private String leavingDate;
  private String cnaps;

  @PrePersist
  public void generateCustomMatriculate() {
    if (matriculate == null) {
      matriculate = String.format("MAT-EMPLOYEE-%s", Instant.now().toEpochMilli());
    }
  }

  public enum Category {
    M1, M2, OS1, OS2, OS3, OP1
  }
}
