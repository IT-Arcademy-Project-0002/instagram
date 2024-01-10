package com.instargram.instargram.Community.Recomment.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Community.Recomment.Model.Repository.ReComment_Like_MapRepository;
import com.instargram.instargram.Community.Recomment.Model.Repository.RecommentRepository;
import com.instargram.instargram.DataNotFoundException;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommentService {
    private final RecommentRepository recommentRepository;
    private final ReComment_Like_MapRepository reCommentLikeMapRepository;

    public Recomment create(Member member, Comment comment, String content) {
        Recomment recomment = new Recomment();
        recomment.setComment(comment);
        recomment.setMember(member);
        recomment.setContent(content);
        recomment.setCreateDate(LocalDateTime.now());
        this.recommentRepository.save(recomment);
        return recomment;
    }

    public Recomment getRecommentById(Long id) {
        Optional<Recomment> recomment = this.recommentRepository.findById(id);
        if (recomment.isPresent()) {
            return recomment.get();
        } else {
            throw new DataNotFoundException("Recomment not found");
        }
    }

    public void delete(Recomment recomment) {
        this.recommentRepository.delete(recomment);
    }
}
