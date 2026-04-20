package com.spring.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationCalendarPayload {

    private Long calendarId;
    private String inviteEmail;
    private Long invitedBy; // 초대한 사람 (로그용)
}
