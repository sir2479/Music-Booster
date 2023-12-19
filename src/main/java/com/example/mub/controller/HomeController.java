package com.example.mub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.mub.model.member.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	//메인페이지 이동
	@GetMapping("/")
	public String home(@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		log.info("인덱스 페이지 출력");		
		log.info("로그인 정보: {}", loginMember);

	    return "index";
	}
		
}
