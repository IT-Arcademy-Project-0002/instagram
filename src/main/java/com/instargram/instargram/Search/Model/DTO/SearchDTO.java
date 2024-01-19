package com.instargram.instargram.Search.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchDTO {

   private Integer searchType;
   private String searchDataIndex;
   private String listName; // 이름 (유저닉네임, 장소명, 해시태그명)
   private String listImage; // 리스트 이미지 (유저프로필사진, 장소마커이미지, 해시태그이미지)
   private String listIntroduction; // 리스트 상세 정보 (유저자기소개, 장소세부주소)
   private String listLocationId; // 장소의 고유번호 (장소일 경우에만 해당, PK와는 다름, 카카오 DB 고유장소번호로서 FK로 활용했다고 간주)
   private String listHashTagId; // 해시태그의 고유번호 (해시태그일 경우에만 해당, PK)

   private LocalDateTime createDate; // 지난 검색 결과 DTO에서만 사용 (레포지토리 종류에 관계없이 내림차순(최신순)으로 출력하기 위함)
}
