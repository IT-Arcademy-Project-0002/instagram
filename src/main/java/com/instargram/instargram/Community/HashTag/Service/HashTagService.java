package com.instargram.instargram.Community.HashTag.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Model.Repository.HashTagRepository;
import com.instargram.instargram.DataNotFoundException;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public List<String> extractHashTagWords(String hashTag) {
        List<String> hashTagWords = new ArrayList<>();

        String[] hashTags = hashTag.split("\\s*#");

        for (String tag : hashTags) {
            if (!tag.trim().isEmpty()) {
                hashTagWords.add(tag.trim());
            }
        }

        return hashTagWords;
    }

    public HashTag create(String name) {
        HashTag hashTag  = new HashTag();
        hashTag.setName(name);
        return this.hashTagRepository.save(hashTag);
    }

    public HashTag exists(String hashTagName) {
        return this.hashTagRepository.findByName(hashTagName);
    }

    public HashTag gethashTag(String hashTagName) {
        HashTag hashTag;
        hashTag = this.hashTagRepository.findByName(hashTagName);
        return hashTag;
    }

    public List<HashTag> searchHashTagList(String kw) {
        List<HashTag> results = new ArrayList<>();
        results.addAll(searchMemberByUsername(kw));
        return results;
    }
    public List<HashTag> searchMemberByUsername(String kw)
    {
        return this.hashTagRepository.findByNameContaining(kw);
    }
}
