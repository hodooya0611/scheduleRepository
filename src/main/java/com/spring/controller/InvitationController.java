package com.spring.controller;


import com.spring.request.AcceptInvitationRequest;
import com.spring.request.InvitationRequest;
import com.spring.security.CustomUserDetails;
import com.spring.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitation")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/send-invitation-mail")
    ResponseEntity<String> sendInvitation(@RequestBody InvitationRequest request,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.sendInvitationMail(request,userDetails);
        return ResponseEntity.ok("공유캘린더 메일이 발송되었습니다.");
    }

    @PostMapping("/invite/accept")
    public ResponseEntity<String> acceptInvitation(@RequestBody AcceptInvitationRequest request,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {

        invitationService.acceptInvitation(request.token(), userDetails);

        return ResponseEntity.ok("공유캘린더 참여가 완료되었습니다.");
    }
}
