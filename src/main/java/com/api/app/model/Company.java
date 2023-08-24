package com.api.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
public class Company implements Serializable {
  @Id
  private String id;
  private String name;
  private String description;
  private String email;
  private String slogan;
  private String address;
  @OneToMany(cascade = CascadeType.ALL)
  private List<PhoneNumber> phoneNumbers;
  private String nif;
  private String stat;
  private String rcs;
  @Type(type = "text")
  private String logo;
}
