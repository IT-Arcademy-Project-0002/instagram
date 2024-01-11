package com.instargram.instargram.Community.Board.Model.Form;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BoardCreateForm extends Board {
    private String content;
    private String TagMember;
    private String hashTag;
    private boolean likeHide;
    private boolean commentDisable;

}
