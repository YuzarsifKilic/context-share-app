package com.yuzarsif.seriesservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer episodeNumber;
    private String name;
    private String url;
    private Date episodeDate;
    private Boolean mostPopular;
    private String storyLine;
    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
}
