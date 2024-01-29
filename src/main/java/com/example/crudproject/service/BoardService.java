package com.example.crudproject.service;

import com.example.crudproject.dto.BoardDto;
import com.example.crudproject.entity.Board;
import com.example.crudproject.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private static final String[] basicBoardCategory = {
            "전체",
            "공지",
            "일반",
            "질문",
            "정보"
    };

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
        for (String boardCategory : basicBoardCategory) {
            if (!this.boardRepository.existsByCategory(boardCategory)) {
                Board board = new Board();
                board.setCategory(boardCategory);
                this.boardRepository.save(board);
            }
        }
    }

    public List<BoardDto> readAll() {
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardRepository.findAll()) {
            boardDtos.add(BoardDto.fromEntity(board));
        }
        return boardDtos;
    }

    public BoardDto read(Long boardId) {
        return BoardDto.fromEntity(boardRepository.findById(boardId).orElseThrow());
    }

}
