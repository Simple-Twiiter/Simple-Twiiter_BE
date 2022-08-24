package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.domain.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    static private Member member1;
    static private Member member2;


    @BeforeAll
    static void setUp(){
        member1 = Member.builder()
                .username("member1")
                .password("password1")
                .userImg("")
                .build();
        member2 = Member.builder()
                .username("member2")
                .password("password2")
                .userImg("")
                .build();
    }

    @Test
    void save(){
        Member returnMember = memberRepository.save(member1);
        String title = "post";
        String contents = "post";
        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .member(returnMember)
                .activate(true)
                .build();

        Post returnPost = postRepository.save(post);
        assertInstanceOf(Long.class, returnPost.getId());
        assertEquals(title, returnPost.getTitle());
        assertEquals(contents, returnPost.getContents());
        assertNull(returnPost.getImgUrl());
        assertTrue(returnPost.getActivate());
    }
    @Test
    void getAll(){
        Member[] memberList= {member1, member1, member2, member2, member2};
        List<Member> returnMemberList = memberRepository.saveAll(Arrays.asList(memberList));

        boolean[] activateList = {true, false, true,false,true};

        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Post post = Post.builder()
                    .contents("post" + i)
                    .title("post" + i)
                    .member(returnMemberList.get(i))
                    .activate(activateList[i])
                    .build();
            postList.add(post);
        }
        postRepository.saveAll(postList);

        PageRequest pageRequest1 = PageRequest.of(0,2);
        List<Post> postList1 = postRepository.findAllByActivateIsTrue(pageRequest1);
        assertEquals(2, postList1.size());

        PageRequest pageRequest2 = PageRequest.of(1,2);
        List<Post> postList2 = postRepository.findAllByActivateIsTrue(pageRequest2);
        assertEquals(1, postList2.size());

        PageRequest pageRequest3 = PageRequest.of(0,20);
        List<Post> postList3 = postRepository.findAllByActivateIsTrue(pageRequest3);
        assertEquals(3, postList3.size());

        for (Post post :postList3) {
            assertTrue(post.getActivate());
            assertInstanceOf(LocalDateTime.class, post.getCreatedAt());
            assertInstanceOf(LocalDateTime.class, post.getModifiedAt());
        }
    }

    @Test
    void getOne(){
        Member[] memberList= {member1, member1, member2, member2, member2};
        List<Member> returnMemberList = memberRepository.saveAll(Arrays.asList(memberList));

        boolean[] activateList = {true, false, true,false,true};

        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Post post = Post.builder()
                    .contents("post" + i)
                    .title("post" + i)
                    .member(returnMemberList.get(i))
                    .activate(activateList[i])
                    .build();
            postList.add(post);
        }
        List<Post> returnPostList = postRepository.saveAll(postList);

        Post post = postRepository.findByIdAndActivateIsTrue(returnPostList.get(0).getId()).orElse(null);
        assertNotNull(post);
        assertTrue(post.getActivate());
        assertInstanceOf(LocalDateTime.class, post.getCreatedAt());
        assertInstanceOf(LocalDateTime.class, post.getModifiedAt());
    }
    @Test
    void update(){
        Member[] memberList= {member1, member1, member2, member2, member2};
        List<Member> returnMemberList = memberRepository.saveAll(Arrays.asList(memberList));

        boolean[] activateList = {true, false, true,false,true};

        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Post post = Post.builder()
                    .contents("post" + i)
                    .title("post" + i)
                    .member(returnMemberList.get(i))
                    .activate(activateList[i])
                    .build();
            postList.add(post);
        }
        List<Post> returnPostList = postRepository.saveAll(postList);
        Long postId = returnPostList.get(0).getId();

        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        assertNotNull(post);

        String newTitle = "update post";
        String newContents = "update post";
        String newUrl = "new URL";
        post.update(newTitle, newContents, newUrl);

        Post updatedPost = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        assertEquals(newTitle, updatedPost.getTitle());
        assertEquals(newContents, updatedPost.getContents());
        assertEquals(newUrl, updatedPost.getImgUrl());

    }
}