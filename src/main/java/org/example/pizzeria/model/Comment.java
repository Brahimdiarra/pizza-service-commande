package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;

    private Long pizzaId;

    private Long userId;

    private String userName;

    private String content;

    private Integer rating;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean approved = false;

    private List<String> photoUrls = new ArrayList<>();
}