package com.example.liveboard.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @GetMapping("/kakao/callback")
  public String kakaoLogin(@RequestParam String code,  HttpServletResponse response) {
    String token =
  }
}
