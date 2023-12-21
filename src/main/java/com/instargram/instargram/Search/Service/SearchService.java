package com.instargram.instargram.Search.Service;


import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Repository.MemberRepository;
import com.instargram.instargram.Search.Model.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    //탐색범위 : 계정(회원명, 회원아이디, 자기소개, 자기소개 카테고리), 게시글(내용), 해시태그(해시태그), 위치(위도, 경도 좌표)
    //해당 서비스 또는 레포지토리의 검색메소드를 차용

    private final MemberRepository memberRepository;

    public List<SearchDTO> searchResult(String keyword) {


        List<Member> memberList = this.memberRepository.findByNicknameContaining(keyword);
        List<Member> memberList2 = this.memberRepository.findByUsernameContaining(keyword);
        List<Member> memberList3 = this.memberRepository.findByIntroductionContaining(keyword);

        // 모든 검색결과를 담기 위한 배열 (이 검색결과는 종류가 달라도, 리스트에 들어가는 정보를 의미한다.)
        // data-order 개념 적용? 데이터를 배열에 저장할 때 식별번호로 전체 카테고리를 포함하여 전달

        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (Member member : memberList) {
            // 각 Member의 nickname을 추출하여 SearchDTO에 추가
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        for (Member member : memberList2) {
            // 각 Member의 nickname을 추출하여 SearchDTO에 추가
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        for (Member member : memberList3) {
            // 각 Member의 nickname을 추출하여 SearchDTO에 추가
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        return searchDTOList;
    }

}
