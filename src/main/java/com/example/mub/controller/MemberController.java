package com.example.mub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mub.model.member.Member;
import com.example.mub.model.member.MemberLogin;
import com.example.mub.model.member.MemberSignup;
import com.example.mub.service.MemberService;

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
		
		
		log.info("signup: {}", signup);
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
		
		
		//Member member = 
		
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
		session.setAttribute("login", member);
		
		log.info("로그인 성공", member);
		
		
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
	
	// 내정보 회원정보 수정
	@PostMapping("update")
	public String update(@Validated @ModelAttribute("update") Member update, 
			BindingResult result) {
			

		return "member/update";
	}
	
}
