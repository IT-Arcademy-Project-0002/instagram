package com.instargram.instargram.Community.Recomment.Model.Repository;

import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    void deleteByCommentId(Long id);
}
