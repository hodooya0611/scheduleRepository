package com.spring.service;

import com.spring.domain.Member;
import com.spring.repository.MemberRepository;
import com.spring.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일 사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(member);
    }
}
