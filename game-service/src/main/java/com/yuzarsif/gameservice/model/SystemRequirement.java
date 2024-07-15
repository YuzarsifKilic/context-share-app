package com.yuzarsif.gameservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SystemRequirement {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "os_id")
    private Os os;

    @ManyToOne
    @JoinColumn(name = "processor_id")
    private Processor processor;

    private Integer memory;
    private Integer storage;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_graphics",
            joinColumns = @JoinColumn(name = "system_requirement_id"),
            inverseJoinColumns = @JoinColumn(name = "graphics_id"))
    private Set<Graphics> graphics;

    @OneToMany(mappedBy = "minSystemRequirement")
    private Set<Game> games;
}
