package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_TagMember_Map;
import com.instargram.instargram.Notice.Model.Repository.Notice_Board_TagMember_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeBoardTagMemberMapService {

    private final Notice_Board_TagMember_MapRepository noticeBoardTagMemberMapRepository;

    public void createNoticeBoardTagMember(Board_TagMember_Map boardTagMemberMap, Notice notice) {

        Notice_Board_TagMember_Map noticeBoardTagMemberMap = new Notice_Board_TagMember_Map();
        noticeBoardTagMemberMap.setBoardTagMember(boardTagMemberMap);
        noticeBoardTagMemberMap.setNotice(notice);
        this.noticeBoardTagMemberMapRepository.save(noticeBoardTagMemberMap);

    }
}
