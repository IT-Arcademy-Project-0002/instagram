package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Data_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Data_MapRepository;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_Like_Member_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_TagMember_Map;
import com.instargram.instargram.Notice.Model.Repository.Notice_Board_Like_Member_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Board_TagMember_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeBoardMapService {

    private final ImageService imageService;
    private final NoticeCommentMapService noticeCommentMapService;
    private final Board_Data_MapRepository boardDataMapRepository;
    private final Notice_Board_Like_Member_MapRepository noticeBoardLikeMemberMapRepository;
    private final Notice_Board_TagMember_MapRepository noticeBoardTagMemberMapRepository;



    public String getNoticeBoardImage(Long noticeId, Integer type) {

        String imagePath = " ";


        if (type == 1) {

            // 보드 자체를 알고 있을때의 로직 (null 예외 관련 추후 refactoring시 개선할 것)
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

        if (type == 4 || type == 10) {

            // 대댓글로부터 보드를 찾는 비즈니스 로직
            Recomment recomment = noticeCommentMapService.getNoticeRecomment(noticeId, type);
            Board boardForRecomment = recomment.getComment().getBoard();
            List<Board_Data_Map> dataMapsForRecomment = boardDataMapRepository.findByBoard(boardForRecomment);

            for (Board_Data_Map dataMap : dataMapsForRecomment) {
                if (dataMap.getDataType() != null && dataMap.getDataType() == 2) {
                    Long id = dataMap.getDataId();
                    imagePath = "/files/img/" + imageService.getImageByID(id).getName();
                    break; // 첫 번째 이미지만 필요하므로 루프 종료
                }
            }
        }

        if (type == 9) {

            Notice_Board_TagMember_Map noticeBoardTagMemberMap = noticeBoardTagMemberMapRepository.findByNoticeId(noticeId);

            if (noticeBoardTagMemberMap  != null) {
                Board board = noticeBoardTagMemberMap .getBoardTagMember().getBoard();
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
