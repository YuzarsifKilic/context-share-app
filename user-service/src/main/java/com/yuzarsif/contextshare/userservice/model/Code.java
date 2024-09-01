package com.yuzarsif.contextshare.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Code {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private Integer code;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
