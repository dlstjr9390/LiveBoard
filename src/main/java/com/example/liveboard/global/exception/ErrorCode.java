package com.example.liveboard.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  NOT_FOUND_USER(HttpStatus.NOT_FOUND,"존재 하지 않는 회원 입니다."),
  FAILED_UPLOAD_IMAGE(HttpStatus.BAD_REQUEST, "파일 업로드에 실패했습니다.");
  private final HttpStatus httpStatus;
  private final String message;
}
