package com.spring.service;

import com.spring.request.MemberRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    void registerMember(MemberRequest memberRequest);
}
