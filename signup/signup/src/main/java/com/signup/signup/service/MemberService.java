package com.signup.signup.service;


import com.signup.signup.dto.MemberDTO;
import com.signup.signup.entity.MemberEntity;
import com.signup.signup.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //    =================================================================================
    //이메일,이름,비밀번호 저장
    public void save(MemberDTO memberDTO) {
        //1.repository save메서드 호출 => entity객체를 넘겨줘야한다. (DTO객체를 Entity객체로 변환)
        //그래서 Entity클래스에 DTO객체를 entity객체로 변환하는 static메서드를 정의하였다.
        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
    }

    //    =================================================================================
    //이메일로 로그인
    public MemberDTO login(MemberDTO memberDTO) {
        //1.회원이 입력한 이메일로 DB에서 조회를 한다.
        //2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단한다.
        MemberEntity byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail != null) {
            if (byMemberEmail.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //비밀번호 일치! entity -> dto 변환후 리턴
                return MemberDTO.toMemberDTO(byMemberEmail);
            } else {
                return null; //비밀번호 불일치
            }
        } else {
            return null; //이메일이 없을때
        }
    }

    //    =================================================================================
    //회원 목록 조회
    public List<MemberDTO> findAll() {
        //엔티티 객체를 DTO객체로 리턴해야 하므로 for문으로 변환해준다.
        List<MemberEntity> memberEntity = memberRepository.findAll();
        List<MemberDTO> memberList = new ArrayList<>();
        for (MemberEntity member : memberEntity) {
            memberList.add(MemberDTO.toMemberDTO(member));
        }
        return memberList;
    }

    //    =================================================================================
    //회원 삭제
    public void deleteMemeber(long id) {
        memberRepository.deleteById(id);
    }

    //    =================================================================================
    //회원 상세 조회
    public MemberDTO findMember(long id) {
        MemberEntity member = memberRepository.findById(id).get();
        return MemberDTO.toMemberDTO(member);
    }

    //    =================================================================================
    //로그인한 회원 정보 가져오기
    public MemberDTO findUpdateMember(String email) {
        MemberEntity member = memberRepository.findByMemberEmail(email);
        return MemberDTO.toMemberDTO(member);
    }

    public MemberDTO updateMember(MemberDTO memberDTO) {

        MemberEntity member = memberRepository.findById(memberDTO.getId()).get();
        member.setMemberName(memberDTO.getMemberName());
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberPassword(memberDTO.getMemberPassword());
        memberRepository.save(member);

        return MemberDTO.toMemberDTO(member);
    }
    //    =================================================================================

    public MemberDTO findByEmail(String email){
        MemberEntity member =  memberRepository.findByMemberEmail(email);
        return MemberDTO.toMemberDTO(member);
    }
}
