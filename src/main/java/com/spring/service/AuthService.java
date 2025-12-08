package com.spring.service;

import com.spring.dto.LoginResponseDto;
import com.spring.request.LoginRequest;
import com.spring.request.PasswordResetRequest;
import com.spring.request.ResetPasswordRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    LoginResponseDto login(LoginRequest request);

    void sendPasswordResetMail(PasswordResetRequest request);

    void resetPassword(ResetPasswordRequest request);
}

