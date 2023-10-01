package com.enigma.resttemplate.service;

import com.enigma.resttemplate.entity.Posts;
import com.enigma.resttemplate.model.response.GetAllDataPostsResponse;
import com.enigma.resttemplate.model.response.PostsResponse;
import com.enigma.resttemplate.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final RestTemplate restTemplate;
        //code redundan kita sudah tidak ada
        //code lebih rapi untuk maintenance
    private final PostsRepository postsRepository;
    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    public ResponseEntity<Posts[]> getAllPost(){
        String apiUrl = BASE_URL + "/posts";
        return getResponseEntity(apiUrl);
    }

    public ResponseEntity<String> getPostsById(Long id){
        String apiUrl = BASE_URL + "/posts/" + id;
        return responseMethodGet(restTemplate.getForEntity(apiUrl, String.class), "Gagal Melakukan permintaan GET BY ID");
    }

    public ResponseEntity<String> getCommentsByPostId(Long postId){
        String apiUrl = BASE_URL + "/comments?postId=" + postId;
        return responseMethodGet(restTemplate.getForEntity(apiUrl, String.class), "Gagal Melakukan permintaan GET BY ID");
    }

    public ResponseEntity<PostsResponse> creatPosts(Posts posts){
        String apiUrl = BASE_URL + "/posts";
        //Mengatur header permintaanya
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //membungkus data permintaan dalam HttpEntity
        HttpEntity<Posts> requesEntity = new HttpEntity<>(posts,headers);
        postsRepository.save(posts);
        //Response Posts
        PostsResponse response = new PostsResponse();
        response.setIdPost(posts.getId());  // Isi dengan ID yang sesuai
        response.setTitlePost(posts.getTitle()); // Menggunakan data dari request
        response.setBodyPost(posts.getBody()); // Menggunakan data dari request
        response.setUserIdPost(posts.getUserId()); // Menggunakan data dari request

        return ResponseEntity.ok(response);
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

    private ResponseEntity<Posts[]> getResponseEntity(String apiUrl) {
        ResponseEntity<Posts[]> responseEntity = restTemplate.getForEntity(apiUrl, Posts[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            Posts[] responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
        }
    }

    public ResponseEntity<GetAllDataPostsResponse> getAllData() {
        // Ambil data dari API terbuka
        ResponseEntity<Posts[]> apiResponse = restTemplate.getForEntity(BASE_URL + "/posts", Posts[].class);
        List<Posts> apiPosts = Arrays.asList(apiResponse.getBody());

        // Ambil data dari database
        List<Posts> databasePosts = postsRepository.findAll();

        // Gabungkan data dari kedua sumber
        GetAllDataPostsResponse combinedData = new GetAllDataPostsResponse();
        combinedData.setApiPosts(apiPosts);
        combinedData.setDatabasePosts(databasePosts);

        return ResponseEntity.ok(combinedData);
    }
}
