package org.example.mapper;



import org.example.dto.LoginRequestDto;
import org.example.dto.MemberRequestDto;
import org.example.entity.Member;
import org.example.request.LoginRequest;
import org.example.request.MemberRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginRequestDto toLoginDto (LoginRequest loginRequest);
}
