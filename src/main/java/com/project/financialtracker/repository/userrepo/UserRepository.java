package com.project.financialtracker.repository.userrepo;

import com.project.financialtracker.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserName(String username);
}
