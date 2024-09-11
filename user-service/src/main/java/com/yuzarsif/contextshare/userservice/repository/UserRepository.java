package com.yuzarsif.contextshare.userservice.repository;

import com.yuzarsif.contextshare.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findAllByIdIn(List<String> ids);
}
