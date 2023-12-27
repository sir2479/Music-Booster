package com.example.mub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.ImageFile;
import com.example.mub.model.member.Member;
import com.example.mub.model.member.MemberLogin;
import com.example.mub.model.member.MemberSignup;
import com.example.mub.model.member.MemberUpdate;
import com.example.mub.repository.FileMapper;
import com.example.mub.service.MemberService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final FileMapper fileMapper;
	
	
	// 회원가입 페이지 이동
	@GetMapping("signup")
	public String signup(Model model) {
		//signup.html
		model.addAttribute("signup", new MemberSignup());
		return "member/signup"; 
	}
	
	//회원가입 실행
	@PostMapping("signup")
	public String signup_action(@Validated @ModelAttribute("signup") MemberSignup signup, 
								BindingResult result) {

		if (result.hasErrors()) {
			return "member/signup";
		}
		
		if (!signup.getMember_email().contains("@")) {
			result.reject("emailError", "@ 누락");
			return "member/signup";
		}
		
		Member member = memberService.findMember(signup.getMember_id());
		
		if (member != null) {
			result.reject("duplicate ID", "이미 가입된 ID 입니다.");
			return "member/signup";
		}
	
		memberService.saveMember(MemberSignup.toMember(signup));
			
		return "redirect:/";
	}
	
	
	// 로그인 페이지 이동
	@GetMapping("login")
	public String login(Model model) {
		model.addAttribute("login", new MemberLogin());		
		
		return "member/login";
	}
	
	
	// 로그인 실행
	@PostMapping("login") 
	public String login_action(@Validated @ModelAttribute("login") MemberLogin memberLogin,
							   BindingResult result,
							   HttpServletRequest request,
							   @RequestParam(defaultValue = "/") String redirectURL) {

		if (result.hasErrors()) {
			return "member/login";
		}
		
		Member member = memberService.findMember(memberLogin.getMember_id());
		
		if (member == null || !member.getPassword().equals(memberLogin.getPassword())) {
			result.reject("memberLoginError", "아이디가 없거나 패스워드가 다릅니다.");	
			
			return "member/login";
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", member);
		
		return "redirect:/";
	}
	
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		
		return "redirect:/";
	}
	

	// 내정보 수정 페이지 이동
	@GetMapping("update")
	public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,						 
            			 Model model) {
		
		if (loginMember == null) {
			return "redirect:/member/login";
		}

		Member member = memberService.findMember(loginMember.getMember_id());
		ImageFile imageFile = fileMapper.findImageFileByMemberId(member.getMember_id());

		model.addAttribute("imageFile", imageFile);
		model.addAttribute("member", member);
	
		return "member/update";
		
	}

	
	// 회원 정보 수정
	@PostMapping("update")
	public String update_action (
					@Validated @ModelAttribute("member") MemberUpdate memberUpdate,
					@RequestParam String member_id,
					@RequestParam(required = false) MultipartFile imageFile,
					@SessionAttribute(value = "loginMember", required = false) Member loginMember,
					BindingResult result,
					HttpServletRequest request) {
		
		log.info("memberUpdate: {}", memberUpdate);
		log.info("member_id: {}", member_id);

	    if (result.hasErrors()) {
	    	log.info("에러발생");
	    	return "member/update";
	    }
	    
		Member member = memberUpdate.toMember(memberUpdate);
        
		log.info("replace 전 : {} ", member);		
		member.setMember_id(member.getMember_id().replace(",", ""));		
        log.info("replace 후 : {} ", member);
        
        ImageFile previousFile = fileMapper.findImageFileByMemberId(member.getMember_id());
		
//        // 이메일 주소에 '@' 문자가 포함되어 있는지 확인한다.
//        if (!memberUpdate.getMember_email().contains("@")) {
//            // BindingResult 객체에 GlobalError 를 추가한다.
//            result.reject("emailError", "이메일 형식이 잘못되었습니다.");
//            // member/joinForm.html 페이지를 리턴한다.
//            return "member/update";
//        }
           
//        // 이메일 중복 확인을 위해 데이터베이스에서 기존 회원 정보 가져오기
//        Member existingMember = memberService.findMemberByEmail(memberUpdate.getMember_email());
//        
//        // 로그인한 멤버와 가져온 멤버가 다른 경우에만 중복 여부를 검사합니다.
//        if (existingMember != null && !existingMember.getMember_id().equals(loginMember.getMember_id())) {
//            // 이메일이 이미 존재하는 경우, 사용자에게 경고 메시지를 전달합니다.
//            result.reject("emailError", "이미 사용 중인 이메일입니다.");
//            return "member/update";
//        }
        
//        // 닉네임 중복 확인을 위해 데이터베이스에서 기존 회원 정보 가져오기
//        Member existingMembernick = memberService.findMemberByNickname(memberUpdate.getNickname());
//        
//        // 로그인한 멤버와 가져온 멤버가 다른 경우에만 중복 여부를 검사합니다.
//        if (existingMembernick != null && !existingMembernick.getMember_id().equals(loginMember.getMember_id())) {
//            // 닉네임 이미 존재하는 경우, 사용자에게 경고 메시지를 전달합니다.
//            result.reject("nicknameError", "이미 사용 중인 닉네임입니다.");
//            return "member/update";
//        }
        
        // 비밀번호와 비밀번호 확인이 일치하지 않을 경우 처리
//        if (member.isPasswordConfirmed
//        		(member.getPassword(), member.getPasswordCheck())
//        		== false) {
//            result.reject("passwordMismatch", "비밀번호와 비밀번호 확인 값이 다릅니다.");
//            return "member/update";
//        }
		
		memberService.updateMember(member, imageFile, previousFile);
		
		return "redirect:/";
        }
	
	
	@PostMapping("delete")
	public String delete(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
											HttpServletRequest request) {	
		logout(request);			
		
		// 로그인한 멤버의 ID를 이용해 계정을 삭제하는 서비스 메서드 호출
	    memberService.deleteMember(loginMember.getMember_id());
		
		return "redirect:/";
	}
	
	
	}



