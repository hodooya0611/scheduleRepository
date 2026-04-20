package com.spring.service;

import com.spring.request.*;
import com.spring.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface InvitationService {

    void sendInvitationMail(InvitationRequest request,
                            @AuthenticationPrincipal CustomUserDetails userDetails);

    void acceptInvitation(String token, @AuthenticationPrincipal CustomUserDetails userDetails);
}

