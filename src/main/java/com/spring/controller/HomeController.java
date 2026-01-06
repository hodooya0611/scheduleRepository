package com.spring.controller;


import com.spring.dto.HomeResponseDto;
import com.spring.request.MemberRequest;
import com.spring.security.CustomUserDetails;
import com.spring.service.HomeService;
import com.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public HomeResponseDto home( @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        return homeService.getHomeData(memberId);
    }
}
