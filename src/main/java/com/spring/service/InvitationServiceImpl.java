package com.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.domain.AuthToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.domain.Calendar;
import com.spring.domain.CalendarMember;
import com.spring.domain.Member;
import com.spring.domain.enums.TokenType;
import com.spring.domain.payload.InvitationCalendarPayload;
import com.spring.repository.AuthTokenRepository;
import com.spring.repository.CalendarMemberRepository;
import com.spring.repository.CalendarRepository;
import com.spring.repository.MemberRepository;
import com.spring.request.InvitationRequest;
import com.spring.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final MailService mailService;
    private final ObjectMapper objectMapper;


    private final AuthTokenRepository authTokenRepository;

    private final CalendarMemberRepository calendarMemberRepository;
    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;


    @Override
    public void sendInvitationMail(InvitationRequest request,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {


        // payload 생성
        InvitationCalendarPayload payload =
                new InvitationCalendarPayload(
                        request.calendarId(),
                        request.email(),
                        userDetails.getId()
                );

        String payloadJson;

        try {
            payloadJson = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("초대 payload 직렬화 실패", e);
        }

        // 토큰 생성
        String token = UUID.randomUUID().toString();

        AuthToken invitationToken = AuthToken.builder()
                .token(token)
                .expiryDate(LocalDateTime.now().plusWeeks(1))
                .type(TokenType.INVITATION_CALENDER)
                .used(false)
                .payload(payloadJson)
                .build();

        authTokenRepository.save(invitationToken);

        //  HTML 링크 생성
        String resetLink = "http://localhost:3000/accept-invitation?token=" + token;
        String html = "<p>공유 캘린더를 승낙해주세용~~</p>"
                + "<a href=\"" + resetLink + "\" "
                + "style=\"display:inline-block; padding:10px 20px; color:white; "
                + "background-color:#4CAF50; text-decoration:none; border-radius:5px;\">"
                + "승낙하오</a>";

        // MailService를 통해 HTML 메일 발송
        try {
            mailService.createMimeMessage(request.email(), "공유 캘린더 초대장", html);
        } catch (Exception e) {
            throw new RuntimeException("메일 발송 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void acceptInvitation(String token, CustomUserDetails userDetails) {

        AuthToken authToken = authTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 토큰"));

        // 만료 체크
        if (authToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("토큰 만료");
        }

        // 중복 사용 체크
        if (authToken.isUsed()) {
            throw new IllegalStateException("이미 사용된 토큰");
        }

        // payload 꺼내기
        InvitationCalendarPayload payload;
        try {
            payload = objectMapper.readValue(
                    authToken.getPayload(),
                    InvitationCalendarPayload.class
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("payload 파싱 실패", e);
        }


        Calendar calendar = calendarRepository.findById(payload.getCalendarId())
                .orElseThrow(() -> new IllegalStateException("캘린더 없음"));

        Member member = memberRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalStateException("유저 없음"));


        if (!payload.getInviteEmail().equals(member.getEmail())) {
            throw new IllegalStateException("초대 대상이 아닙니다.");
        }

        boolean exists = calendarMemberRepository
                .existsByCalendarAndMember(calendar, member);

        if (exists) {
            throw new IllegalStateException("이미 참여된 캘린더입니다.");
        }

        // 캘린더 참여
        CalendarMember calendarMember = CalendarMember.builder()
                .calendar(calendar)
                .member(member)
                .role("EDITOR") // 기본 권한
                .build();

        calendarMemberRepository.save(calendarMember);

        authToken.setUsed(true);
        authTokenRepository.save(authToken);
    }
}
