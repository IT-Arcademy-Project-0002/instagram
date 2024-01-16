package com.instargram.instargram.Community.Recomment.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Community.Recomment.Model.Repository.ReComment_Like_MapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Recomment_Like_MapService {
    private final ReComment_Like_MapRepository reCommentLikeMapRepository;
    public ReComment_Like_Map create(Recomment recomment, Member member) {
        ReComment_Like_Map reCommentLikeMap = new ReComment_Like_Map();
        reCommentLikeMap.setRecomment(recomment);
        reCommentLikeMap.setMember(member);
        this.reCommentLikeMapRepository.save(reCommentLikeMap);
        return reCommentLikeMap;
    }

    public void delete(ReComment_Like_Map map) {
        this.reCommentLikeMapRepository.delete(map);
    }

    public ReComment_Like_Map exists(Recomment recomment, Member member) {
        return this.reCommentLikeMapRepository.findByRecommentAndMember(recomment, member);
    }

    public int countLikesForReComment(Recomment recomment) {
        List<ReComment_Like_Map> likeMaps = reCommentLikeMapRepository.findByRecomment(recomment);
        return likeMaps.size();
    }
}
