package com.techxtor.consumeapi.controller;

import com.techxtor.consumeapi.model.Post;
import com.techxtor.consumeapi.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class RestTemplateConsumeApiController {

    @Value("${jsonplaceholder.url}")
    private String jsonplaceholderURL;

    // http://localhost:8080/users/1
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        String url = UriComponentsBuilder.fromHttpUrl(jsonplaceholderURL).path("/users/{id}").build(id).toString();
        return new RestTemplate()
                .getForObject(url, User.class);
    }


    // http://localhost:8080/users
    // http://localhost:8080/users?id=1
    @GetMapping
    public User[] getUsers(@RequestParam Optional<Integer> id) {
        String url;
        if (id.isPresent()) {
            url = UriComponentsBuilder.fromHttpUrl(jsonplaceholderURL).path("/{uri}?id={qp}").build().toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("uri", "users");
            uriVariables.put("qp", id.get().toString());
            ResponseEntity<User[]> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity, User[].class, uriVariables);
            return response.getBody();
        } else {
            url = UriComponentsBuilder.fromHttpUrl(jsonplaceholderURL).path("/users").build().toString();
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<User[]> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity, User[].class);
            return response.getBody();
        }

    }


    // http://localhost:8080/users
    @PostMapping()
    public ResponseEntity<User> addUsers(@RequestBody User user) {
        String url = UriComponentsBuilder.fromHttpUrl(jsonplaceholderURL).path("/users").build().toString();
        RestTemplate restTemplate = new RestTemplate();
        /*
            User addedUser = restTemplate.postForObject(url, user, User.class);
            return new ResponseEntity<>(addedUser, HttpStatus.OK);
        */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity, User.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}
