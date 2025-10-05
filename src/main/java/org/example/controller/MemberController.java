package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.mapper.MemberMapper;
import org.example.request.MemberRequest;
import org.example.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> registerMember(@RequestBody MemberRequest member) {
         memberService.registerMember(member);
        return ResponseEntity.noContent().build();
    }
}
