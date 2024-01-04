package com.instargram.instargram.Search.Model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {

   private Integer searchType;
   private String listName; // 이름 (유저닉네임, 장소명, 해시태그명)
   private String listImage; // 리스트 이미지 (유저프로필사진, 장소마커이미지, 해시태그이미지)
   private String listIntroduction; // 리스트 상세 정보 (유저자기소개, 장소세부주소, -)_
   private String listLocationId;; // 장소의 고유번호 (장소일 경우에만 해당)
}
