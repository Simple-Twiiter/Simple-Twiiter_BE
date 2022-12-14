package com.example.simpletwiter_be.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    private String imgUrl;
    @Column(nullable = false)
    private Boolean activate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false,updatable = false)
    private Member member;

    public void disable(){
        this.activate = false;
    }

    public void update(String title, String contents, String imgUrl){
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }

    @Column
    private int heartCount;

    public void update(List<PostHeart> postHearts){
        this.heartCount = postHearts.size();
    }
}
