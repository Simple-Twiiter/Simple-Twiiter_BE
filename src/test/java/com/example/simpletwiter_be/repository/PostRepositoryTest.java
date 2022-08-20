package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    private static Member member1;
    private static Member member2;
    private static List<Post> postList;

    @BeforeAll
    void setUp(){
        member1 = Member.builder()
                .id(1L)
                .username("member1")
                .build();
        member2 = Member.builder()
                .id(1L)
                .username("member2")
                .build();
        Member[] memberList= {member1, member1, member2, member2, member2};
        boolean[] activateList = {true, false, true,false,true};

        for (int i = 0; i < 5; i++) {
            Post post = Post.builder()
                    .contents("post"+i)
                    .title("post"+i)
                    .member(memberList[i])
                    .activate(activateList[i])
                    .build();
            postList.add(post);
        }
    }

    @Test
    void save(){
        String title = "post";
        String contents = "post";
        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .member(member1)
                .activate(true)
                .build();

        Post returnPost = postRepository.save(post);
        assertInstanceOf(Long.class, returnPost.getId());
        assertEquals(title, returnPost.getTitle());
        assertEquals(contents, returnPost.getContents());
        assertNull(returnPost.getImgUrl());
        assertTrue(returnPost.isActivate());
    }

    @Test
    void update(){}
}