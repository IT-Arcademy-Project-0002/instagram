package com.instargram.instargram.Community.Recomment.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Community.Recomment.Model.Repository.ReComment_Like_MapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Recomment_Like_MapService {
    private final ReComment_Like_MapRepository reCommentLikeMapRepository;
    public void create(Recomment recomment, Member member) {
        ReComment_Like_Map reCommentLikeMap = new ReComment_Like_Map();
        reCommentLikeMap.setRecomment(recomment);
        reCommentLikeMap.setMember(member);
        this.reCommentLikeMapRepository.save(reCommentLikeMap);
    }

    public void delete(ReComment_Like_Map map) {
        this.reCommentLikeMapRepository.delete(map);
    }

    public ReComment_Like_Map exists(Recomment recomment, Member member) {
        return this.reCommentLikeMapRepository.findByRecommentAndMember(recomment, member);
    }
}
