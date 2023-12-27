package com.example.mub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.mub.model.file.ImageFile;
import com.example.mub.model.member.Member;
import com.example.mub.repository.FileMapper;
import com.example.mub.repository.WishlistMapper;
import com.example.mub.service.ArtistService;
import com.example.mub.service.MusicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final FileMapper fileMapper;

	//메인페이지 이동
	@GetMapping("/")
	public String home(@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		log.info("인덱스 페이지 출력");		
		log.info("로그인 정보: {}", loginMember);
		
		if(loginMember != null) {
			ImageFile imagefile = fileMapper.findImageFileByMemberId(loginMember.getMember_id());
			if(imagefile != null) {
				loginMember.setImage_file_saved_name(imagefile.getFile_saved_name());
			}
		}

	    return "index";
	}
		
}
