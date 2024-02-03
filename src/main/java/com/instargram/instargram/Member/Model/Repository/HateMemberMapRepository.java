package com.instargram.instargram.Member.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Hate_Member_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HateMemberMapRepository extends JpaRepository<Hate_Member_Map, Long> {

    List<Hate_Member_Map> findByOwnerUsername(String username);

    Hate_Member_Map findByOwnerUsernameAndHateMemberUsername(String loginUser, String target);

    boolean existsByOwnerUsernameAndHateMemberUsername(String owner, String target);
}
