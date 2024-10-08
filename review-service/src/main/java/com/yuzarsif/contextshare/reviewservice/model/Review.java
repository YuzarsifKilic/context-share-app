package com.yuzarsif.contextshare.reviewservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("reviews")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Review {

    private String id;
    private String userId;
    private ContextType contextType;
    private Long contextId;
    private String comment;
    private Float rating;
    private List<Reply> replies;

}
