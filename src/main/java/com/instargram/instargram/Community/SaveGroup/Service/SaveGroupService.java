package com.instargram.instargram.Community.SaveGroup.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Community.SaveGroup.Model.Repository.SaveGroupRepository;
import com.instargram.instargram.DataNotFoundException;
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

    public SaveGroup create(String groupName) {
        SaveGroup saveGroup = new SaveGroup();
        saveGroup.setName(groupName);
        return this.saveGroupRepository.save(saveGroup);
    }
}
