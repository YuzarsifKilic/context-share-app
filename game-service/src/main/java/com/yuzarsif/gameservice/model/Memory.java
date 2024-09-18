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
public class Memory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Integer size;
    private String description;
    @OneToMany(mappedBy = "memory", cascade = CascadeType.REMOVE)
    private Set<SystemRequirement> systemRequirements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memory memory = (Memory) o;
        return Objects.equals(id, memory.id) && Objects.equals(size, memory.size) && Objects.equals(description, memory.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, description);
    }

    @Override
    public String toString() {
        return "Memory{" +
                "id=" + id +
                ", size=" + size +
                ", description='" + description + '\'' +
                '}';
    }
}
