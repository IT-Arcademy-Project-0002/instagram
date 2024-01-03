package com.instargram.instargram.Search.Model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {

   private Integer searchType;
   private String listName; // 유저 닉네임
   private String listImage; // 프로필 이미지
   private String listIntroduction; // 상세 정보
   private String listLocationId;; // 장소의 고유번호
}
