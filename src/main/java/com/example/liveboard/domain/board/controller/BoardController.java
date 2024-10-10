package com.example.liveboard.domain.board.controller;

import com.example.liveboard.domain.board.dto.BoardRequestDto;
import com.example.liveboard.domain.board.entity.Board;
import com.example.liveboard.domain.board.service.BoardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardController {

  BoardService boardService;
  @PostMapping
  public void createBoard(BoardRequestDto boardRequestDto){

    boardService.createdBoard(boardRequestDto);
  }
}
