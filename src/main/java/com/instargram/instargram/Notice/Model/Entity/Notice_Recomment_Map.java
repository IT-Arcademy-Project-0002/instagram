package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice_Recomment_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Notice notice;

    @ManyToOne
    private Recomment recomment;

}
