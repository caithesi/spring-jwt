package com.thesi.jwtsec.service.impl;

import com.thesi.jwtsec.domain.Role;
import com.thesi.jwtsec.domain.User;
import com.thesi.jwtsec.repo.RoleRepo;
import com.thesi.jwtsec.repo.UserRepo;
import com.thesi.jwtsec.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepo userRepo;
  private final RoleRepo roleRepo;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User saveUser(User user) {
    log.info("Saving new user {}", user.getUserName());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving new role {}", role.getName());
    return roleRepo.save(role);
  }

  @Override
  public void addRoleToUser(String userName, String roleName) {
    log.info("Add {} role to {} user", roleName, userName);
    User user = userRepo.findByUserName(userName);
    Role role = roleRepo.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public User getUser(String userName) {
    return userRepo.findByUserName(userName);
  }

  @Override
  public List<User> getUser() {
    return userRepo.findAll();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.of(userRepo.findByUserName(username))
             .map(e -> new org.springframework.security.core.userdetails.User(
               e.getUserName(),
               e.getPassword(),
               e.getRoles().stream()
                 .map(Role::getName)
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toList())))
             .orElseThrow(() -> new UsernameNotFoundException("not_found"));
  }
}
