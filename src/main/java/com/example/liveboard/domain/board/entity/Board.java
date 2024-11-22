package com.example.liveboard.domain.board.entity;

import com.example.liveboard.domain.board.dto.BoardRequestDto;
import com.example.liveboard.global.timestamp.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long boardId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private String author;

  @Column(nullable = false)
  private long view;

  public Board(BoardRequestDto boardRequestDto){
    this.title = boardRequestDto.getTitle();
    this.author = boardRequestDto.getAuthor();
    this.content = boardRequestDto.getContent();
    this.view = 0;
  }

}
