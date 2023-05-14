package com.signup.signup.entity;

import com.signup.signup.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
/*테이블 이름을 설정안하면 클래스이름으로 생성된다.*/
@Table(name="member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id;

    @Column(unique = true) //unique 키 부여
    private String memberEmail;

    private String memberPassword;
    private String memberName;

    //DTO객체를 ENTITY객체로 변환 static메서드 => 요즘 이런방식을 많이 사용한다.
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

}
