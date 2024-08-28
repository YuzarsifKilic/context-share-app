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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_processors",
            joinColumns = @JoinColumn(name = "system_requirement_id"),
            inverseJoinColumns = @JoinColumn(name = "processor_id"))
    private Set<Processor> processors;

    private Integer memory;
    private Integer storage;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_graphics",
            joinColumns = @JoinColumn(name = "system_requirement_id"),
            inverseJoinColumns = @JoinColumn(name = "graphics_id"))
    private Set<Graphics> graphics;

    @OneToMany(mappedBy = "minSystemRequirement")
    private Set<Game> games;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemRequirement that = (SystemRequirement) o;
        return Objects.equals(id, that.id) && Objects.equals(memory, that.memory) && Objects.equals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memory, storage);
    }

    @Override
    public String toString() {
        return "SystemRequirement{" +
                "id=" + id +
                ", memory=" + memory +
                ", storage=" + storage +
                '}';
    }
}
