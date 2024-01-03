package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Repository.BoardRepository;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.DataNotFoundException;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board create(Member member, String content, Location location, boolean likeHide, boolean commentDisalbe){
        Board board = new Board();
        board.setMember(member);
        board.setContent(content);
        board.setCreateDate(LocalDateTime.now());
        board.setLocation(location);
        board.setLikeHide(likeHide);
        board.setCommentDisable(commentDisalbe);
        return this.boardRepository.save(board);
    }
    public List<Board> getBoard() {
        return this.boardRepository.findAll();
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
        return this.boardRepository.findByMemberOrderByPinDateOrCreateDateDesc(member);
    }

    public void PinStateChange(Long id)
    {
        Board board = getBoardById(id);

        board.setPin(!board.isPin());
        board.setPinDate(LocalDateTime.now());

        boardRepository.save(board);
    }

    public void KeepStateChange(Long id)
    {
        Board board = getBoardById(id);

        board.setKeep(!board.isKeep());

        boardRepository.save(board);
    }

    public void LikeStateChange(Long id) {
        Board board = getBoardById(id);

        board.setLikeHide(!board.isLikeHide());
        boardRepository.save(board);
    }
    public void delete(Board board) {
        this.boardRepository.delete(board);
    }

    public void CommentDisableStateChange(Long id) {
        Board board = getBoardById(id);

        board.setCommentDisable(!board.isCommentDisable());
        boardRepository.save(board);
    }

    public Integer getSizeByMember(Member member)
    {
        return boardRepository.countByMemberAndPin(member, false);

    }

    public List<Board> getBoardsByFollowerIds(List<Long> followerIds) {
        return boardRepository.findByMember_IdIn(followerIds);

    }

    public List<Board> getBoardsByMember(Member member) {
        return this.boardRepository.findByMember(member);
    }
}
