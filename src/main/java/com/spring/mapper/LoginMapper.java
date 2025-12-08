package com.spring.mapper;



import com.spring.dto.LoginRequestDto;
import com.spring.request.LoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginRequestDto toLoginDto (LoginRequest loginRequest);
}
