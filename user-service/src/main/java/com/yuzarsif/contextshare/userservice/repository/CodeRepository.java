package com.yuzarsif.contextshare.userservice.repository;

import com.yuzarsif.contextshare.userservice.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {

    void deleteByCreatedAtBefore(LocalDateTime dateTime);

    Optional<Code> findByEmail(String email);
}
