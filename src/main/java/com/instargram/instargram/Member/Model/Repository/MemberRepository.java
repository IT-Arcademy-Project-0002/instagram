package com.instargram.instargram.Member.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String id);

    Member findByProviderAndProviderId(String provider, String providerId);
}
