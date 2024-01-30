package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_TagMember_MapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class Board_TagMember_MapService {
    private final Board_TagMember_MapRepository boardTagMemberMapRepository;


    public List<String> extractMentionedWords(String content) {
        List<String> mentionedWords = new ArrayList<>();

        for(String username : content.split("@"))
        {
            if(!username.isEmpty())
            {
                mentionedWords.add(username);
            }
        }

        return mentionedWords;
    }

    public Board_TagMember_Map create(Board board, Member tagMember) {
        Board_TagMember_Map map = new Board_TagMember_Map();
        map.setBoard(board);
        map.setTagMember(tagMember);
        return this.boardTagMemberMapRepository.save(map);
    }
    public void delete(Board board) {
        List<Board_TagMember_Map> existingAssociations = boardTagMemberMapRepository.findByBoard(board);
        this.boardTagMemberMapRepository.deleteAll(existingAssociations);
    }
}
