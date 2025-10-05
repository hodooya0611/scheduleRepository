package org.example.service;

import org.example.dto.LoginResponseDto;
import org.example.dto.ScheduleResponseDto;
import org.example.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    LoginResponseDto login(LoginRequest request);
}

