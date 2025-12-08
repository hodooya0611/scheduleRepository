package com.spring.request;

public record MemberRequest(
        String memberId,

        String name,

        String email,

        String password
) {


}
