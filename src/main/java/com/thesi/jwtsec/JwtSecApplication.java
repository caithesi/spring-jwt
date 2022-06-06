package com.thesi.jwtsec;

import com.thesi.jwtsec.domain.Role;
import com.thesi.jwtsec.domain.User;
import com.thesi.jwtsec.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JwtSecApplication {

  public static void main(String[] args) {
    SpringApplication.run(JwtSecApplication.class, args);
  }

  @Bean
  CommandLineRunner run(UserService userService) {
    return args -> {
      userService.saveRole(new Role(null, "ROLE_USER"));
      userService.saveRole(new Role(null, "ROLE_MANAGER"));
      userService.saveRole(new Role(null, "ROLE_ADMIN"));
      userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

      userService.saveUser(new User(null, "John", "john", "1234", new ArrayList<>()));
      userService.saveUser(new User(null, "Will", "will", "1234", new ArrayList<>()));
      userService.saveUser(new User(null, "Yanpanpan", "yanpanpan", "1234", new ArrayList<>()));

      userService.addRoleToUser("john", "ROLE_USER");
      userService.addRoleToUser("will", "ROLE_MANAGER");
      userService.addRoleToUser("yanpanpan", "ROLE_ADMIN");

    };
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
