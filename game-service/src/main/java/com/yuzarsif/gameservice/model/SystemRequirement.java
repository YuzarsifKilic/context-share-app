package com.yuzarsif.gameservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
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
    @JoinColumn(name = "memory_id")
    private Memory memory;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_processors",
            joinColumns = @JoinColumn(name = "system_requirement_id"),
            inverseJoinColumns = @JoinColumn(name = "processor_id"))
    private Set<Processor> processors;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_graphics",
            joinColumns = @JoinColumn(name = "system_requirement_id"),
            inverseJoinColumns = @JoinColumn(name = "graphics_id"))
    private Set<Graphics> graphics;

    @OneToMany(mappedBy = "minSystemRequirement")
    private Set<Game> minSystemRequirementGames;

    @OneToMany(mappedBy = "recommendedSystemRequirement")
    private Set<Game> recommendedRequirementGames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemRequirement that = (SystemRequirement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SystemRequirement{" +
                "id=" + id +
                '}';
    }
}
