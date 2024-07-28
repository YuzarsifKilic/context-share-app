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
public class Os {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String brand;
    private String version;
    @OneToMany(mappedBy = "os", cascade = CascadeType.REMOVE)
    private Set<SystemRequirement> systemRequirements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Os os = (Os) o;
        return Objects.equals(id, os.id) && Objects.equals(brand, os.brand) && Objects.equals(version, os.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, version);
    }

    @Override
    public String toString() {
        return "Os{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
