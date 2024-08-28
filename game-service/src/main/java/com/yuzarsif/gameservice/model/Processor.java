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
public class Processor {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String brand;
    private String version;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_processors",
            joinColumns = @JoinColumn(name = "processor_id"),
            inverseJoinColumns = @JoinColumn(name = "system_requirement_id"))
    private Set<SystemRequirement> systemRequirements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processor processor = (Processor) o;
        return Objects.equals(id, processor.id) && Objects.equals(brand, processor.brand) && Objects.equals(version, processor.version) && Objects.equals(description, processor.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, version, description);
    }

    @Override
    public String toString() {
        return "Processor{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
