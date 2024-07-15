package com.yuzarsif.movieservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Star {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String bio;
    private Date birthDate;
    private Float height;
    private String bornPlace;
    private String imageUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "movie_star",
            joinColumns = @JoinColumn(name = "star_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies;

    @Override
    public String toString() {
        return "Star{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", birthDate=" + birthDate +
                ", height=" + height +
                ", bornPlace='" + bornPlace + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Star star = (Star) o;
        return Objects.equals(id, star.id) && Objects.equals(name, star.name) && Objects.equals(bio, star.bio) && Objects.equals(birthDate, star.birthDate) && Objects.equals(height, star.height) && Objects.equals(bornPlace, star.bornPlace) && Objects.equals(imageUrl, star.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bio, birthDate, height, bornPlace, imageUrl);
    }
}
