package com.instargram.instargram.Community.HashTag.Service;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Model.Repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public List<String> extractMentionedWords(String hashTag) {
        List<String> mentionedWords = new ArrayList<>();

        String[] hashTags = hashTag.split("\\s*#");

        for (String tag : hashTags) {
            if (!tag.trim().isEmpty()) {
                mentionedWords.add("#" + tag.trim());
            }
        }

        return mentionedWords;
    }

    public HashTag create(String name) {
        HashTag hashTag  = new HashTag();
        hashTag.setName(name);
        return this.hashTagRepository.save(hashTag);
    }
}
