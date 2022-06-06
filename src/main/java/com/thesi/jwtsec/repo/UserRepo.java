package com.thesi.jwtsec.repo;

import com.thesi.jwtsec.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  User findByUserName(String userName);
}
