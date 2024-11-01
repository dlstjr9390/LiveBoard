package com.example.liveboard.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
  private final String password;

  private final String email;
}
