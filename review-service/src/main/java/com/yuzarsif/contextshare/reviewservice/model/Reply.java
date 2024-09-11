package com.yuzarsif.contextshare.reviewservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("replies")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Reply {

    private String id;
    private String userId;
    private String comment;

}
