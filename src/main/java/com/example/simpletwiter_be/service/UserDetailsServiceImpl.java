package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.UserDetailsImpl;
<<<<<<< HEAD
import com.example.simpletwiter_be.repository.MemberRepository;
=======
import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.repository.UsersRepository;
>>>>>>> 3f57b00f289599c7383c33a09645384cd94a4d68
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
