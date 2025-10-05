package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.config.JwtUtil;
import org.example.dto.LoginResponseDto;
import org.example.entity.Member;
import org.example.mapper.LoginMapper;
import org.example.repository.MemberRepository;
import org.example.request.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequest request) {

        var requestDto = loginMapper.toLoginDto(request);

        var member = memberRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 찾을 수 없습니다. id=" + requestDto.getMemberId()));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호 틀림");
        }

        String token = jwtUtil.generateToken(member.getMemberId(), member.getRole());

        return new LoginResponseDto(token, member.getMemberId(), member.getRole());
    }
}
