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
    @OneToMany(mappedBy = "processor", cascade = CascadeType.REMOVE)
    private Set<SystemRequirement> systemRequirements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processor processor = (Processor) o;
        return Objects.equals(id, processor.id) && Objects.equals(brand, processor.brand) && Objects.equals(version, processor.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, version);
    }

    @Override
    public String toString() {
        return "Processor{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
