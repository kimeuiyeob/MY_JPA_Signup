package com.signup.signup.dto;


import com.signup.signup.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data는 Lombok 라이브러리에서 제공하는 어노테이션이다.
//@Data : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 모두를 자동으로 적용한다.
@Data
//기본 생성자
@NoArgsConstructor
//필드의 모든 값들을 매게변수로 받는 생성자
@AllArgsConstructor
/*data transfer object 여기 객체로 값들을 전송한다.*/
public class MemberDTO {

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }

}
