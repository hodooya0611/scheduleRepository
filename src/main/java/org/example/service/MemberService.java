package org.example.service;

import org.example.entity.Member;
import org.example.entity.Schedule;
import org.example.request.MemberRequest;
import org.example.request.ScheduleRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    void registerMember(MemberRequest memberRequest);
}
