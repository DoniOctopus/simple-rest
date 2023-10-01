package com.enigma.resttemplate.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PostsResponse {
    private Integer idPost;
    private String titlePost;
    private String bodyPost;
    private int userIdPost;
}
