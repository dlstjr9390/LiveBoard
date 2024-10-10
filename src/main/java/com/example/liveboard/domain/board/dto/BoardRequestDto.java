package com.example.liveboard.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
  private final String title;

  private final String author;

  private final String content;

  public BoardRequestDto(String title, String author, String content){
    this.title = title;
    this.author = author;
    this.content = content;
  }
}
