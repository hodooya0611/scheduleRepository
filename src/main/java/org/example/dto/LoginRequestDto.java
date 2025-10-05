package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {


    private Long id;


    private String memberId;


    private String name;


    private String email;


    private String password;


    private String role;
}
