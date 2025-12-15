package com.spring.service;

import com.spring.dto.HomeResponseDto;
import com.spring.request.MemberRequest;
import org.springframework.stereotype.Service;

@Service
public interface HomeService {

    HomeResponseDto getHomeData(
            Long loginUserId);
}
