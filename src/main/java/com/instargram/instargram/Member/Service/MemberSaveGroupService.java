package com.instargram.instargram.Member.Service;

import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Entity.Member_SaveGroup_Map;
import com.instargram.instargram.Member.Model.Repository.MemberSaveGroupMapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
public class MemberSaveGroupService {

    private final MemberSaveGroupMapRepository memberSaveGroupMapRepository;

    public void Create(Member member, SaveGroup saveGroup)
    {
        Member_SaveGroup_Map memberSaveGroupMap = new Member_SaveGroup_Map();
        memberSaveGroupMap.setMember(member);
        memberSaveGroupMap.setSaveGroup(saveGroup);

        memberSaveGroupMapRepository.save(memberSaveGroupMap);
    }

    public List<SaveGroup> getSaveGroupByMember(Member member)
    {
        return memberSaveGroupMapRepository.findByMember(member).stream().map(Member_SaveGroup_Map::getSaveGroup).toList();
    }
}
