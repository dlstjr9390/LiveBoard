package com.example.liveboard.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class UserService {
  private final RestTemplate restTemplate;

  @Value("${REST_API_KEY}")
  private String REST_API_KEY;

  public String kakaoLogin(String code) throws JsonProcessingException {
    String accessToken = getToken(code);
    return accessToken;
  }

  private String getToken(String code) throws JsonProcessingException {
    URI uri = UriComponentsBuilder
        .fromUriString("https://kauth.kakao.com")
        .path("/oauth/token")
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add(
        "Content-Type",
        "application/x-www-form-urlencoded;charset=utf-8"
    );

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", REST_API_KEY );
    body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
    body.add("code",code);

    RequestEntity<MultiValueMap<String,String>> requestEntity = RequestEntity
        .post(uri)
        .headers(headers)
        .body(body);

    ResponseEntity<String> response = restTemplate.exchange(
        requestEntity,
        String.class
    );

    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
    return jsonNode.get("access_token").asText();
  }
}
