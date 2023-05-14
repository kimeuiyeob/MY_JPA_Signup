package com.signup.signup.controller;


import com.signup.signup.dto.MemberDTO;
import com.signup.signup.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
/*@RequiredArgsConstructor 어노테이션을 사용한 "생성자 주입"*/
@RequiredArgsConstructor
public class MemberController {

    /*@RequiredArgsConstructor final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션*/
    /*@Autowired는 하나하나 다 붙여줘야하지만 @RequiredArgsConstructor로 하면 final만 붙이면 된다.*/
    private final MemberService memberService;

    //====================================================================================================
    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    //    @PostMapping("/save")
    /*@RequestParam 폼태그 name의 속성의 키값으로 데이터 값을 받는다.*/
    /*사용자가 요청시 전달하는 값을 Handler(Controller)의 매개변수로 1:1 맵핑할때 사용되는 어노테이션*/
//    public String save(@RequestParam String memberEmail, String memberPassword, String memberName) {
//         System.out.println("이메일 : " +  memberEmail);
//         System.out.println("비밀번호 : " +  memberPassword);
//         System.out.println("이름 : " +  memberName);
//        return "save";
//}
    @PostMapping("/save")
    /*@ModelAttribute는 사용자가 요청시 전달하는 값을 오브젝트 형태로 매핑해주는 어노테이션*/
    /* *주의*  =>  form태그 name이랑 DTO 필드값의 명들이 같아야 매핑이 된다!! */
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "login";
    }

    //====================================================================================================
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session,Model model) {
        /*세션은 정보를 세션아이디와 매핑하여 서버에 저장하여 사용한다.*/
        MemberDTO member = memberService.findByEmail(memberDTO.getMemberEmail());
        MemberDTO loginResult = memberService.login(memberDTO);

        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            model.addAttribute("member",member);
            return "main";
        } else {
            return "login"; //이메일, 비밀번호 불일치로 널값이 넘어올때
        }
    }

    //====================================================================================================
    //회원 목록 출력
    @GetMapping
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        //데이터를 HTML로 가져가야할 경우 Model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    //====================================================================================================
    //회원 삭제
    @GetMapping("/delete")
    public String deleteMember(@RequestParam Long id) {
        memberService.deleteMemeber(id);
        return "redirect:/member";
    }

    //====================================================================================================
    //회원 상세 조회
    @GetMapping("/detail")
    public String memberDetail(@RequestParam Long id, Model model) {
        MemberDTO member = memberService.findMember(id);
        model.addAttribute("member", member);
        return "detail";
    }

    //====================================================================================================
    //회원 정보 수정
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("loginEmail");
        MemberDTO member = memberService.findUpdateMember(email);
        model.addAttribute("member", member);
        return "update";
    }
    @PostMapping("/update")
    public String updateMember(@ModelAttribute MemberDTO memberDTO) {
        memberService.updateMember(memberDTO);
        return "redirect:/member";
    }
    //====================================================================================================
    //로그 아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //세션 무효화
        session.invalidate();
        return "index";
    }
}
