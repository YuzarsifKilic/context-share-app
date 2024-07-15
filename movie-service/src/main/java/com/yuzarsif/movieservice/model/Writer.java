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
public class Writer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Date birthDate;
    private String bio;
    private String bornPlace;
    private Float height;
    private String url;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "movie_writer",
            joinColumns = @JoinColumn(name = "writer_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies;

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", bio='" + bio + '\'' +
                ", bornPlace='" + bornPlace + '\'' +
                ", height=" + height +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return Objects.equals(id, writer.id) && Objects.equals(name, writer.name) && Objects.equals(birthDate, writer.birthDate) && Objects.equals(bio, writer.bio) && Objects.equals(bornPlace, writer.bornPlace) && Objects.equals(height, writer.height) && Objects.equals(url, writer.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, bio, bornPlace, height, url);
    }
}
