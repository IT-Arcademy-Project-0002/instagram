package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_TagMember_MapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
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

        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            mentionedWords.add(matcher.group(1));
        }

        return mentionedWords;
    }

    public Board_TagMember_Map create(Board board, Member tagMember) {
        Board_TagMember_Map map = new Board_TagMember_Map();
        map.setBoard(board);
        map.setTagMember(tagMember);
        return this.boardTagMemberMapRepository.save(map);
    }
}
