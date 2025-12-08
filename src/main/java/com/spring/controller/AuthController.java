package com.spring.controller;

import com.spring.request.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import com.spring.dto.LoginResponseDto;
import com.spring.request.LoginRequest;
import com.spring.request.PasswordResetRequest;
import com.spring.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // 비밀번호 재설정 요청 메일
    @PostMapping("/password-reset-request")
    ResponseEntity<String> passwordResetRequest(@RequestBody PasswordResetRequest request) {
        authService.sendPasswordResetMail(request);
        return ResponseEntity.ok("비밀번호 재설정 메일이 발송되었습니다.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("success");
    }
}
