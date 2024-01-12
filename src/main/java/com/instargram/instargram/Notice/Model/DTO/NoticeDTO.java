package com.instargram.instargram.Notice.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class NoticeDTO {

    // 공통 객체 + 팔로우 요청(8)
    private Long id; // PK
    private Integer type; // 알림 타입
    private Member requestMember;
    private LocalDateTime createTime; // 알림 헤더를 생성하기 위한 변수
    private String elapsedTime; // 경과 시간을 알려주기 위한 변수
    private Integer period; // 1 이번주, 2 이번달, 3 지난 기간 등...

    private boolean follow;
    private boolean follower;

    private String boardContent; // 보드 내용

    // 게시글 좋아요(1) + 게시글 댓글(2) + 댓글 좋아요(3) + 댓글 대댓글(4) + 게시글 멤버태그(9) + 댓글 대댓글 좋아요(10)
    private String commentContent; // 댓글 내용
    private String boardMainImage; // 게시글 메인 이미지

    // 댓글 대댓글(4) + 댓글 대댓글 좋아요(10)
    private String recommentContent; // 대댓글 내용

    // 디엠 왔을 때 : 5
    // 디엠 좋아요 : 6
    // 스토리 좋아요 : 7

    // 본문에 언급된 회원의 멤버태그를 추출하고 개인페이지로 이동되는 링크를 만들어주는 함수
    public void processContent(String boardContent) {

        if (boardContent != null) {
            // 정규표현식을 사용하여 '@' 뒤의 단어 추출
            final String regex = "@(\\S+)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(boardContent);

            // 모든 '@' 뒤의 단어에 대해 반복
            while (matcher.find()) {
                final String username = matcher.group(1);

                // '@' 뒤의 단어를 하이퍼링크로 변경
                final String link = "<a href=\"/member/page/" + username + "\"><span>@" + username + "</span></a>";

                // 추출한 '@' 뒤의 단어를 원래 문자열에서 하이퍼링크로 교체
                boardContent = boardContent.replace("@" + username, link);
            }
        }
        this.boardContent = boardContent;
    }

    // 대댓글에 언급된 회원의 멤버태그를 추출하고 개인페이지로 이동되는 링크를 만들어주는 함수
    public void processRecommentContent(String recommentContent) {

        if (recommentContent != null) {
            // 정규표현식을 사용하여 '@' 뒤의 단어 추출
            final String regex = "@(\\S+)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(recommentContent);

            // 모든 '@' 뒤의 단어에 대해 반복
            while (matcher.find()) {
                final String username = matcher.group(1);

                // '@' 뒤의 단어를 하이퍼링크로 변경
                final String link = "<a href=\"/member/page/" + username + "\"><span>@" + username + "</span></a>";

                // 추출한 '@' 뒤의 단어를 원래 문자열에서 하이퍼링크로 교체
                recommentContent = recommentContent.replace("@" + username, link);
            }
        }
        this.recommentContent = recommentContent;
    }
}
