package com.instargram.instargram.Search.Service;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Repository.MemberRepository;
import com.instargram.instargram.Search.Model.DTO.CoordinatesDTO;
import com.instargram.instargram.Search.Model.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
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


    @Value("${kakao.api.key}")
    private String kakaoLocalKey;

    private final String uri1 = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private final String uri2 = "https://dapi.kakao.com/v2/local/search/address.json";


    public CoordinatesDTO getCoordinateByKeyword() throws JSONException {

        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "KakaoAK " + kakaoLocalKey;
        String keyword = "유성온천역";

        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri1)
                .queryParam("query",keyword)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        // body에서 좌표 뽑아내기 (index를 지정하지 않으면 모든값을 다 가져올수 있을듯? 공식문서 참조)
        // https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword
        JSONArray documents = json.getJSONArray("documents");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");

        return new CoordinatesDTO(x, y);
    }

    public CoordinatesDTO getCoordinateByAddress() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "KakaoAK " + kakaoLocalKey;
        String address = "대전광역시 유성구 계룡로 지하97 (봉명동)";

        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri2)
                .queryParam("query",address)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        // body에서 좌표 뽑아내기
        JSONArray documents = json.getJSONArray("documents");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");

        return new CoordinatesDTO(x, y);
    }

    public List<SearchDTO> searchResult(String keyword) {


        List<Member> memberList = this.memberRepository.findByNicknameContaining(keyword);
        List<Member> memberList2 = this.memberRepository.findByUsernameContaining(keyword);
        List<Member> memberList3 =  this.memberRepository.findByIntroductionContaining(keyword);

        // 모든 검색결과를 담기 위한 배열 (이 검색결과는 종류가 달라도, 리스트에 들어가는 정보를 의미한다.)
        // data-order 개념 적용? 데이터를 배열에 저장할 때 식별번호로 전체 카테고리를 포함하여 전달

        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (Member member : memberList) {
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setProfileimage(member.getImage() != null ? member.getImage().getName() : "");
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        for (Member member : memberList2) {
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setProfileimage(member.getImage() != null ? member.getImage().getName() : "");
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        for (Member member : memberList3) {
            SearchDTO sd = new SearchDTO();
            sd.setUsername(member.getUsername());
            sd.setProfileimage(member.getImage() != null ? member.getImage().getName() : "");
            sd.setIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }

        return searchDTOList;
    }

}
