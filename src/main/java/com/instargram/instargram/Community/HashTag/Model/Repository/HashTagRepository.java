package com.instargram.instargram.Community.HashTag.Model.Repository;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}
