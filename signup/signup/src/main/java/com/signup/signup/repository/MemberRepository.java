package com.signup.signup.repository;

import com.signup.signup.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//SpringJPA Hibernate구현체가 CRUD를 실행한다.
//카멜표기법을 HiberNate가 underbar로 교환해서 데이터베이스에 저장한다 => 옵션에서 바꿀수있다.
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //이메일로 회원 정보 조회
//    Optional<MemberEntity> findByMemberEmail(String email);
    public MemberEntity findByMemberEmail(String email);

}
