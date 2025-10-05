package org.example.mapper;



import org.example.dto.MemberRequestDto;
import org.example.entity.Member;
import org.example.request.MemberRequest;
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
