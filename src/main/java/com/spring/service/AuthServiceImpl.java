package com.spring.service;

import com.spring.domain.AuthToken;
import com.spring.domain.Member;
import com.spring.domain.enums.TokenType;
import com.spring.repository.AuthTokenRepository;
import com.spring.request.ResetPasswordRequest;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.spring.config.JwtUtil;
import com.spring.dto.LoginResponseDto;
import com.spring.mapper.LoginMapper;
import com.spring.repository.MemberRepository;
import com.spring.request.LoginRequest;
import com.spring.request.PasswordResetRequest;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final AuthTokenRepository authTokenRepository;
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MailService mailService;

    @Override
    public LoginResponseDto login(LoginRequest request) {

        var requestDto = loginMapper.toLoginDto(request);

        var member = memberRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 찾을 수 없습니다. id=" + requestDto.getMemberId()));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호 틀림");
        }

        String token = jwtUtil.generateToken(member.getId(), member.getRole());

        return new LoginResponseDto(token, member.getMemberId(), member.getRole());
    }

    @Override
    public void sendPasswordResetMail(PasswordResetRequest request) {
        Member member = memberRepository.findByMemberId(request.memberId()).orElseThrow(
                ()->  new EntityNotFoundException("해당 아이디를 찾을 수 없습니다. id=" + request.memberId()));

        String token = UUID.randomUUID().toString();

        AuthToken resetToken = AuthToken.builder()
                .token(token)
                .member(member)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .type(TokenType.PASSWORD_RESET)
                .used(false)
                .build();

        authTokenRepository.save(resetToken);

        // 4️⃣ HTML 링크 생성
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        String html = "<p>안녕하세요, " + member.getName() + "님</p>"
                + "<p>비밀번호 재설정을 위해 아래 버튼을 클릭해주세요.</p>"
                + "<a href=\"" + resetLink + "\" "
                + "style=\"display:inline-block; padding:10px 20px; color:white; "
                + "background-color:#4CAF50; text-decoration:none; border-radius:5px;\">"
                + "비밀번호 재설정</a>";

        // 5️⃣ MailService를 통해 HTML 메일 발송
        try {
            mailService.createMimeMessage(request.email(), "비밀번호 재설정 안내", html);
        } catch (Exception e) {
            throw new RuntimeException("메일 발송 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        AuthToken authToken = authTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new RuntimeException("토큰이 유효하지 않습니다."));

        Member member = authToken.getMember();

        member.setPassword(passwordEncoder.encode(request.newPassword()));
        memberRepository.save(member);

        // 토큰은 1회 사용이므로 삭제
        authTokenRepository.delete(authToken);
    }
}
