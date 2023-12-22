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

    //탐색범위 : 계정(회원명, 회원아이디, 자기소개, 자기소개 카테고리), 게시글(내용), 해시태그(해시태그), 위치(위도, 경도, 장소고유식별번호)
    //해당 서비스 또는 레포지토리의 검색메소드를 차용

    // data-order의 개념을 사용하여 분리해야 하는가?
    // 서버에서 받아온 데이터를 리스트 생성
    // 세 가지 분류
    // 1) 데이터형이 계정이라면 계정 프로필 링크 생성 (~/username, 유저아이디)
    // 2) 해시태그라면 explore 페이지 연결, (~/explore/tags/태그명)
    // 3) 장소라면 explore 페이지 연결 (~/explore/locations/장소분류고유번호) openstreetmap 사용
    //    (괄호안의 숫자는 위치 DB의 ID로 추정, 일반적인 게시물의 의미를 생각하였을 때)



    private final MemberRepository memberRepository;

    public List<SearchDTO> searchResult(String keyword) {


        List<Member> memberList = this.memberRepository.findByNicknameContaining(keyword);
        List<Member> memberList2 = this.memberRepository.findByUsernameContaining(keyword);
        List<Member> memberList3 = this.memberRepository.findByIntroductionContaining(keyword);

        // 모든 검색결과를 담기 위한 배열 (이 검색결과는 종류가 달라도, 리스트에 들어가는 정보를 의미한다.)
        // data-order 개념 적용? 데이터를 배열에 저장할 때 식별번호로 전체 카테고리를 포함하여 전달

        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (Member member : memberList) {
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        for (Member member : memberList2) {
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        for (Member member : memberList3) {
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        return searchDTOList;
    }

}
