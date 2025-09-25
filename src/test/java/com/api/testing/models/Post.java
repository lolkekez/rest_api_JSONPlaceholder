package com.api.testing.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    
    private Integer id;
    private Integer userId;
    private String title;
    private String body;

    public boolean isValid() {
        return userId != null && title != null && !title.trim().isEmpty() && body != null;
    }
}
