package com.example.simpletwiter_be.domain;

import com.example.simpletwiter_be.dto.request.CommentRequestDto;
import com.example.simpletwiter_be.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Users member;



    @JoinColumn(name = "post_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;


    public void update(CommentRequestDto commentRequestDto){this.content = commentRequestDto.getContent();}

    public boolean validateMember(Users member) {
        return !this.member.equals(member);
    }
}
