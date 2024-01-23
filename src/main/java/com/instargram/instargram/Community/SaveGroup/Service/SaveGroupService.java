package com.instargram.instargram.Community.SaveGroup.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Community.SaveGroup.Model.Repository.SaveGroupRepository;
import com.instargram.instargram.DataNotFoundException;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveGroupService {
    private final SaveGroupRepository saveGroupRepository;

    public SaveGroup getGroupName(Long saveGroupId) {
        Optional<SaveGroup> saveGroup = this.saveGroupRepository.findById(saveGroupId);
        if (saveGroup.isPresent()) {
            return saveGroup.get();
        } else {
            throw new DataNotFoundException("saveGroup not found");
        }
    }

    public SaveGroup create(String groupName, Member member) {
        SaveGroup saveGroup = new SaveGroup();
        saveGroup.setName(groupName);
        saveGroup.setMember(member);
        return this.saveGroupRepository.save(saveGroup);
    }

    public SaveGroup findByName(String name) {
        return saveGroupRepository.findByName(name);
    }
}
