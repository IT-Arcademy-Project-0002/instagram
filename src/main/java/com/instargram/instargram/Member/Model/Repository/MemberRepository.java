package com.instargram.instargram.Member.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String id);

    Member findByEmail(String email);

    Member findByPhoneNumber(String num);

    Member findByProviderAndProviderId(String provider, String providerId);

    List<Member> findByUsernameContaining(String keyword);
    List<Member> findByNicknameContaining(String keyword);
    List<Member> findByIntroductionContaining(String keyword);

}
