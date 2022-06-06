package com.thesi.jwtsec.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String userName;
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> roles = new ArrayList<>();
}
