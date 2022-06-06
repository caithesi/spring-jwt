package com.thesi.jwtsec.api;

import com.thesi.jwtsec.domain.Role;
import com.thesi.jwtsec.domain.User;
import com.thesi.jwtsec.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserResource {
  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUser() {
    return ResponseEntity.ok().body(userService.getUser());
  }

  @PostMapping("/users")
  public ResponseEntity<User> saveUser(@RequestBody User user) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
    return ResponseEntity.created(uri).body(userService.saveUser(user));
  }

  @PostMapping("/roles")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles").toUriString());
    return ResponseEntity.created(uri).body(userService.saveRole(role));
  }


}
