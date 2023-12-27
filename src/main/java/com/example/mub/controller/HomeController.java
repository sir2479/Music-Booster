package com.example.mub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.mub.model.board.Board;
import com.example.mub.model.board.BoardCategory;
import com.example.mub.model.file.ImageFile;
import com.example.mub.model.member.Member;
import com.example.mub.model.music.Music;
import com.example.mub.repository.BoardMapper;
import com.example.mub.repository.FileMapper;
import com.example.mub.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final FileMapper fileMapper;
	private final BoardMapper boardMapper;
	private final MusicService musicService;

	//메인페이지 이동
	@GetMapping("/")
	public String home(Model model,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		log.info("메인 페이지");
		
		// 멤버 프로필사진
		if(loginMember != null) {
			ImageFile imagefile = fileMapper.findImageFileByMemberId(loginMember.getMember_id());
			if(imagefile != null) {
				loginMember.setImage_file_saved_name(imagefile.getFile_saved_name());
			}
		}
		
		// 게시판
		List<Board> homeBoard = boardMapper.homeBoard(BoardCategory.NEWS);
		if(homeBoard.size() > 6) {
			homeBoard = homeBoard.subList(0, 9);
		}	
		model.addAttribute("boards", homeBoard);	
		
		// 음악 랭킹
		List<Music> rankMusics = musicService.findMusicsDescLike();
		List<ImageFile> rankImageFiles = new ArrayList<>();
		
		for(int i = 0 ; i < rankMusics.size() ; i++) {
			rankImageFiles.add(musicService.findImageFileByMusicId(rankMusics.get(i).getMusic_id()));
			rankMusics.get(i).setImage_file_saved_name(rankImageFiles.get(i).getFile_saved_name());
		}		
		if(rankMusics.size() > 6) {
			rankMusics = rankMusics.subList(0, 6);
		}	
		model.addAttribute("rankMusics", rankMusics);

	    return "index";
	}
		
}
