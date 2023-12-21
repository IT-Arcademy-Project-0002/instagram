package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Repository.BoardRepository;
import com.instargram.instargram.DataNotFoundException;
import com.instargram.instargram.Member.Model.DTO.UserPageDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board create(Member member, String content){
        Board board = new Board();
        board.setMember(member);
        board.setContent(content);
        board.setCreate_date(LocalDateTime.now());
        return this.boardRepository.save(board);
    }
    public List<Board> getBoard() {
        return this.boardRepository.findAll();
//        return this.boardRepository.findAllByCreateDateDesc();
    }
    public Board getBoardById(Long id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }
    public List<Board> getBoardByMember(Member member) {
        return this.boardRepository.findByMember(member);
    }
}
