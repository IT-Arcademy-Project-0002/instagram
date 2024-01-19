package com.instargram.instargram.Member.Config.SpringSecurity;

import com.instargram.instargram.Member.Config.Enum.UserRole;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class MemberSecurityService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = null;
        if (username.matches("[0-9]+")) {
            member = this.memberService.getMemberByPhoneNumber(username);
        } else if (username.contains("@")) {
            member = this.memberService.getMemberByEmail(username);
        } else {
            member = this.memberService.getMemberByUsername(username);
        }

        if(member == null)
        {
            member = this.memberService.getMemberByUsername(username);

            if(member == null)
            {
                throw new UsernameNotFoundException(" 유저가 없습니다.");
            }
        }

        return new PrincipalDetails(member);
    }
}