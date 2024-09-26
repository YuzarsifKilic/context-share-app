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
public class Video {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String videoUrl;
    private String audioUrl;
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id) && Objects.equals(videoUrl, video.videoUrl) && Objects.equals(audioUrl, video.audioUrl) && Objects.equals(thumbnailUrl, video.thumbnailUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, videoUrl, audioUrl, thumbnailUrl);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", videoUrl='" + videoUrl + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
