package com.enigma.resttemplate.model.response;

import com.enigma.resttemplate.entity.Posts;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GetAllDataPostsResponse {
    private List<Posts> apiPosts;
    private List<Posts> databasePosts;

    // Getter dan Setter
}
