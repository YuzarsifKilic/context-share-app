package com.yuzarsif.contextshare.userservice.repository;

import com.yuzarsif.contextshare.userservice.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long> {

    List<Code> deleteByCreatedAtBefore(LocalDateTime dateTime);
}
