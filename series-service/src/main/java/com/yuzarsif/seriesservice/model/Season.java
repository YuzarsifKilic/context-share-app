package com.yuzarsif.seriesservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seasonNumber;
    private String url;
    private Integer episodeCount;
    private Boolean mostPopular;
    private String storyLine;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @OneToMany(mappedBy = "season")
    private Set<Episode> episodes;
}
