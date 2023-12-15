package com.example.mub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {
	
	
	// 아티스트 페이지 이동
		@GetMapping("artist-home")
		public String signup() {
			
			
			return "artist/artist-home"; 
		}
}
