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
public class Storage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Integer size;
    private String description;
    @OneToMany(mappedBy = "storage", cascade = CascadeType.REMOVE)
    private Set<SystemRequirement> systemRequirements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return Objects.equals(id, storage.id) && Objects.equals(size, storage.size) && Objects.equals(description, storage.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, description);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", size=" + size +
                ", description='" + description + '\'' +
                '}';
    }
}
