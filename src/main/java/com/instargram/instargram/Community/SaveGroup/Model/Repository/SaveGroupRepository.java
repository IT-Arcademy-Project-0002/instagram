package com.instargram.instargram.Community.SaveGroup.Model.Repository;

import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveGroupRepository extends JpaRepository<SaveGroup, Long> {

    SaveGroup findByName(String groupName);
}
