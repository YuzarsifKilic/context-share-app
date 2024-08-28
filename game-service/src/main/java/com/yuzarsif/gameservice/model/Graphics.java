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
public class Graphics {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String brand;
    private String version;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "system_requirement_graphics",
            joinColumns = @JoinColumn(name = "graphics_id"),
            inverseJoinColumns = @JoinColumn(name = "system_requirement_id"))
    private Set<SystemRequirement> systemRequirements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graphics graphics = (Graphics) o;
        return Objects.equals(id, graphics.id) && Objects.equals(brand, graphics.brand) && Objects.equals(version, graphics.version) && Objects.equals(description, graphics.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, version, description);
    }

    @Override
    public String toString() {
        return "Graphics{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
