package org.example.request;

import jakarta.persistence.Column;

public record MemberRequest(
        String memberId,

        String name,

        String email,

        String password
) {


}
