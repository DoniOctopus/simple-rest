package com.enigma.resttemplate.service;

import com.enigma.resttemplate.entity.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final RestTemplate restTemplate;
        //code redundan kita sudah tidak ada
        //code lebih rapi untuk maintenance
    public ResponseEntity<String> getAllPost(){
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        return responseMethodGet(restTemplate.getForEntity(apiUrl, String.class), "Gagagl Mengakses API");
    }

    public ResponseEntity<String> getPostsById(Long id){
        String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + id;
        return responseMethodGet(restTemplate.getForEntity(apiUrl, String.class), "Gagal Melakukan permintaan GET BY ID");
    }

    public ResponseEntity<String> getCommentsByPostId(Long postId){
        String apiUrl = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;
        return responseMethodGet(restTemplate.getForEntity(apiUrl, String.class), "Gagal Melakukan permintaan GET BY ID");
    }

    public ResponseEntity<String> creatPosts(Posts posts){
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        //Mengatur header permintaanya
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //membungkus data permintaan dalam HttpEntity
        HttpEntity<Posts> requesEntity = new HttpEntity<>(posts,headers);
        //Response Posts
        return responseMethodGet(restTemplate.postForEntity(apiUrl, requesEntity, String.class), "Gagal Melakukan permintaan GET BY ID");
    }

    private ResponseEntity<String> responseMethodGet(ResponseEntity<String> restTemplate, String Gagagl_Mengakses_API) {
        ResponseEntity<String> responseEntity = restTemplate;
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            String responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body(Gagagl_Mengakses_API);
        }
    }
}
