package com.thesi.jwtsec.service;

import com.thesi.jwtsec.domain.Role;
import com.thesi.jwtsec.domain.User;

import java.util.List;

public interface UserService {
  User saveUser(User user);

  Role saveRole(Role role);

  void addRoleToUser(String userName, String roleName);

  User getUser(String userName);

  List<User> getUser();
}
