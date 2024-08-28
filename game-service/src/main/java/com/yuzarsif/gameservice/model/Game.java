package com.yuzarsif.gameservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Date releaseDate;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String mainImage;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_developer",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<Developer> developers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_publisher",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private Set<Publisher> publishers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_feature",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> features;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platform> platforms;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_audio_language",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "audio_language_id"))
    private Set<Language> audioLanguages;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "game_subtitle_language",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "subtitle_language_id"))
    private Set<Language> subtitleLanguages;

    @ManyToOne
    @JoinColumn(name = "min_system_requirement_id")
    private SystemRequirement minSystemRequirement;

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
    private Set<Store> stores;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(name, game.name) && Objects.equals(releaseDate, game.releaseDate) && Objects.equals(description, game.description) && Objects.equals(mainImage, game.mainImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseDate, description, mainImage);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", description='" + description + '\'' +
                ", mainImage='" + mainImage + '\'' +
                '}';
    }
}
