package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.mapper.MemberMapper;
import org.example.repository.MemberRepository;
import org.example.request.MemberRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public void registerMember(MemberRequest memberRequest) {
        var memberRequestDto = memberMapper.toMemberDto(memberRequest); // JSON → DTO
        var memberRequestEntity = memberMapper.toMemberEntity(memberRequestDto);   // DTO → Entity
        memberRequestEntity.setPassword(passwordEncoder.encode(memberRequestEntity.getPassword()));
            memberRepository.save(memberRequestEntity);
    }
}
