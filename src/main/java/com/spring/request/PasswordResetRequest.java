package com.spring.request;

public record PasswordResetRequest(
        String memberId,

        String email

) {
}
