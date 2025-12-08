package com.spring.mapper;



import com.spring.dto.MemberRequestDto;
import com.spring.domain.Member;
import com.spring.request.MemberRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberRequestDto toMemberDto (MemberRequest memberRequest);

    default Member toMemberEntity (MemberRequestDto memberRequestDto){
        return Member.builder()
                .memberId(memberRequestDto.getMemberId())
                .password(memberRequestDto.getPassword())
                .role("USER")
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .build();
    }
}
