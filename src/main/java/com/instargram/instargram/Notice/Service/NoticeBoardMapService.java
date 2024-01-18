package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Data_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Data_MapRepository;
import com.instargram.instargram.Community.Board.Model.Repository.Board_TagMember_MapRepository;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_Like_Member_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_TagMember_Map;
import com.instargram.instargram.Notice.Model.Repository.Notice_Board_Like_Member_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Board_TagMember_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeBoardMapService {

    private final ImageService imageService;
    private final NoticeCommentMapService noticeCommentMapService;
    private final Board_Data_MapRepository boardDataMapRepository;
    private final Notice_Board_Like_Member_MapRepository noticeBoardLikeMemberMapRepository;
    private final Notice_Board_TagMember_MapRepository noticeBoardTagMemberMapRepository;
    private final Board_TagMember_MapRepository boardTagMemberMapRepository;

    public String getBoardContent(Long noticeId, Integer type) {

        String boardContent = " ";

        if (type == 6) {
            // 태그멤버로부터 보드를 찾는 비즈니스 로직
            Notice_Board_TagMember_Map noticeBoardTagMemberMap = noticeBoardTagMemberMapRepository.findByNoticeId(noticeId);

            if (noticeBoardTagMemberMap != null) {
                Board board = noticeBoardTagMemberMap.getBoardTagMember().getBoard();
                List<Board_TagMember_Map> boardTagMemberMapList = boardTagMemberMapRepository.findByBoardId(board.getId());

                // boardTagMemberMapList에는 언급된 회원들의 리스트 배열이 있고 그 이름을 모두 가져와서 boardContent에 추가
                for (Board_TagMember_Map boardTagMemberMap : boardTagMemberMapList) {
                    boardContent += "@" + boardTagMemberMap.getTagMember().getUsername() + " ";
                }

                // 게시글 내용 가져오기
                boardContent += board.getContent() + " ";

                return boardContent;
            }
        }

        return boardContent;
    }


    public String getNoticeBoardImage(Long noticeId, Integer type) {

        String imagePath = " ";


        if (type == 1) {

            // 보드 자체를 알고 있을때의 비즈니스 로직
            Notice_Board_Like_Member_Map noticeBoardLikeMemberMap = noticeBoardLikeMemberMapRepository.findByNoticeId(noticeId);

            if (noticeBoardLikeMemberMap != null) {
                Board board = noticeBoardLikeMemberMap.getBoardLikeMember().getBoard();
                List<Board_Data_Map> dataMaps = boardDataMapRepository.findByBoard(board);

                for (Board_Data_Map dataMap : dataMaps) {
                    if (dataMap.getDataType() != null && dataMap.getDataType() == 2) {
                        Long id = dataMap.getDataId();
                        imagePath = "/files/img/" + imageService.getImageByID(id).getName();
                        break; // 첫 번째 이미지만 필요하므로 루프 종료
                    }
                }
            }
        }

        if (type == 2 || type == 3) {

            // 댓글로부터 보드를 찾는 비즈니스 로직
            Comment comment = noticeCommentMapService.getNoticeComment(noticeId, type);

            if (comment != null) {
                Board boardForComment = comment.getBoard();
                List<Board_Data_Map> dataMapsForComment = boardDataMapRepository.findByBoard(boardForComment);

                for (Board_Data_Map dataMap : dataMapsForComment) {
                    if (dataMap.getDataType() != null && dataMap.getDataType() == 2) {
                        Long id = dataMap.getDataId();
                        imagePath = "/files/img/" + imageService.getImageByID(id).getName();
                        break; // 첫 번째 이미지만 필요하므로 루프 종료
                    }
                }
            }
        }

        if (type == 4 || type == 5) {

            // 대댓글로부터 보드를 찾는 비즈니스 로직
            Recomment recomment = noticeCommentMapService.getNoticeRecomment(noticeId, type);

            if (recomment != null && recomment.getComment() != null) {
                Comment commentForRecomment = recomment.getComment();
                Board boardForRecomment = commentForRecomment.getBoard();

                if (boardForRecomment != null) {
                    List<Board_Data_Map> dataMapsForRecomment = boardDataMapRepository.findByBoard(boardForRecomment);

                    for (Board_Data_Map dataMap : dataMapsForRecomment) {
                        if (dataMap.getDataType() != null && dataMap.getDataType() == 2) {
                            Long id = dataMap.getDataId();
                            imagePath = "/files/img/" + imageService.getImageByID(id).getName();
                            break; // 첫 번째 이미지만 필요하므로 루프 종료
                        }
                    }
                }
            } 
        }

        if (type == 6) {

            // 태그멤버로부터 보드를 찾는 비즈니스 로직
            Notice_Board_TagMember_Map noticeBoardTagMemberMap = noticeBoardTagMemberMapRepository.findByNoticeId(noticeId);

            if (noticeBoardTagMemberMap  != null) {
                Board board = noticeBoardTagMemberMap.getBoardTagMember().getBoard();
                List<Board_Data_Map> dataMaps = boardDataMapRepository.findByBoard(board);

                for (Board_Data_Map dataMap : dataMaps) {
                    if (dataMap.getDataType() != null && dataMap.getDataType() == 2) {
                        Long id = dataMap.getDataId();
                        imagePath = "/files/img/" + imageService.getImageByID(id).getName();
                        break; // 첫 번째 이미지만 필요하므로 루프 종료
                    }
                }
            }
        }

        return imagePath;


    }

    public void createNoticeBoardLikeMember(BoardLikeMemberMap boardLikeMemberMap, Notice notice) {

        Notice_Board_Like_Member_Map noticeBoardLikeMap = new Notice_Board_Like_Member_Map();
        noticeBoardLikeMap.setBoardLikeMember(boardLikeMemberMap);
        noticeBoardLikeMap.setNotice(notice);
        this.noticeBoardLikeMemberMapRepository.save(noticeBoardLikeMap);

    }
    public void createNoticeBoardTagMember(Board_TagMember_Map boardTagMemberMap, Notice notice) {

        Notice_Board_TagMember_Map noticeBoardTagMemberMap = new Notice_Board_TagMember_Map();
        noticeBoardTagMemberMap.setBoardTagMember(boardTagMemberMap);
        noticeBoardTagMemberMap.setNotice(notice);
        this.noticeBoardTagMemberMapRepository.save(noticeBoardTagMemberMap);

    }

}
