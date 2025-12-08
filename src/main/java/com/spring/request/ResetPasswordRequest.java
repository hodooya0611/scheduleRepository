package com.spring.request;

public record ResetPasswordRequest(

        String newPassword,

        String token

) {
}
