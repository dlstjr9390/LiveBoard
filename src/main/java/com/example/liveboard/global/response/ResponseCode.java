package com.example.liveboard.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

  OK(200, "요청 성공"),

  LOGIN(200, "로그인 성공"),

  GET_PROFILE(200, "회원정보 가져오기 성공"),

  SIGNUP(201, "회원가입 성공");


  private final int HttpStatus;

  private final String message;

}