package com.instargram.instargram.Community.Location.Model.Entity;


import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Recomment.ReComment_Like_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kakao Geocoding 기반 REST API 이용
    // 참조문서 (https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword)

    // 장소 ID
    private String locationId;

    // 장소명
    private String placeName;

    // 주소 (도로명 주소 우선, 도로명 없을 경우 일반주소 등록)
    private String address;

    // X좌표값, 경도(longitude, ex 132.35)
    private String x;

    // Y좌표값, 위도(latitude, ex 35.24)
    private String y;

    @OneToOne
    private Board board;
}
