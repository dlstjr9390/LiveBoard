package com.example.liveboard.global.exception;

import lombok.Getter;

@Getter
public class BisException extends RuntimeException {

  private final ErrorCode errorCode;

  public BisException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
