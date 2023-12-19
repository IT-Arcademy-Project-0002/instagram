package com.instargram.instargram.Member.Config.SpringSecurity;

import com.instargram.instargram.Member.Config.Enum.UserRole;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        Member member = this.memberService.getMember(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
            if ("admin".equals(username)) {
                authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
//            else if(member.getRole().equals(UserRole.MANAGER.getValue())){
//                authorities.add(new SimpleGrantedAuthority(UserRole.MANAGER.getValue()));
//        }
            else {
                authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
            }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}