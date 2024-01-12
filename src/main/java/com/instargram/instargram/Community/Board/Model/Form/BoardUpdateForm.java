package com.instargram.instargram.Community.Board.Model.Form;

import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardUpdateForm {
    private String ModifyContent;
    private String ModifyTagMember;
    private String ModifyHashTag;
    private boolean ModifyLikeHide;
    private boolean ModifyCommentDisable;
}
