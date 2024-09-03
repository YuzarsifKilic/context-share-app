package com.yuzarsif.contextshare.userservice.model;

import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private Purpose purpose;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
