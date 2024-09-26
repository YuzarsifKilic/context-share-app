package com.yuzarsif.gameservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String thumbnailUrl;
    private String fullUrl;
    private String type;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) && Objects.equals(thumbnailUrl, photo.thumbnailUrl) && Objects.equals(fullUrl, photo.fullUrl) && Objects.equals(type, photo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thumbnailUrl, fullUrl, type);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", fullUrl='" + fullUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
