package com.example.simpletwiter_be.domain;


import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@Entity

public class Follow extends Timestamped{
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private FollowId id;

    @Builder
    public Follow(Member member){
        this.Memver = member;
    }

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberid;

    @ManyToOne
    @JoinColumn(name = "follow_id")
    private Member followid;

    public Follow(Member memberid, Member followid){
        this.followid = followid;
        this.memberid = memberid;
    }



}
