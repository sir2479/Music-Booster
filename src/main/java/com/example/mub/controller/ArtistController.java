package com.example.mub.controller;

import java.util.List;

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

import com.example.mub.model.artist.Artist;
import com.example.mub.model.artist.ArtistUpdateForm;
import com.example.mub.model.artist.ArtistWriteForm;
import com.example.mub.model.member.Member;
import com.example.mub.repository.ArtistMapper;
import com.example.mub.service.ArtistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {
	
	private final ArtistService artistService;
	private final ArtistMapper artistMapper;
	
	// 아티스트 페이지 이동
	@GetMapping("artist-home")
	public String artist(@RequestParam(value="search", defaultValue="")String search,
						 Model model) {
		
		//int total = artistService.getTotal(search);
		
		List<Artist> artists = artistMapper.findAllArtists(search);
		
		model.addAttribute("artists", artists);
		
		return "artist/artist-home"; 
	}
	
	@GetMapping("artist-write")
	public String writeForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
							Model model) {
		
		model.addAttribute("artist-write", new ArtistWriteForm());
		
		return "artist/artist-write";
	}
	
	@PostMapping("artist-write")
	public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
            			@Validated @ModelAttribute("artist-write") ArtistWriteForm artistWriteForm,
            			BindingResult result) {
		
		if (result.hasErrors()) {
			return "artist/artist-write";
		}
		
		
		Artist artist = ArtistWriteForm.toArtist(artistWriteForm);
		
		artist.setArtist_member_id(loginMember.getMember_id());
		
		artistService.saveArtist(artist);
		
		
		return "redirect:/artist/artist-home";
	}
	
	@GetMapping("artist-read")
	public String read(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
					   @RequestParam Long artist_id,
					   Model model) {
		
		Artist artist = artistService.readArtist(artist_id);
		
		if (artist != null) {
			log.info("게시글 없음");
			return "redirect:/artist";
		}
		
		model.addAttribute("artist", artist);
	
		return "artist/artist-read";
	}
	
	@GetMapping("artist-update")
	public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
							 @RequestParam Long artist_id,
							 Model model) {
		
		log.info("id: {}", artist_id);
		
		Artist artist = artistService.findArtist(artist_id);
		if (artist == null || !artist.getArtist_id().equals(loginMember.getMember_id())) {
			log.info("수정 권한 없음");
			return "redirect:/artist/artist-home";
		}
		
		model.addAttribute("artist", Artist.toArtistUpdateForm(artist));
		
		return "artist/artist-update";
	}

			
	@PostMapping("artist-update")
	public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
            @RequestParam Long artist_id,
            @Validated @ModelAttribute("artist") ArtistUpdateForm updateArtist,
            BindingResult result) {
	
	
	Artist artist = artistService.findArtist(artist_id);
	
	
	
	artist.setArtist_name(updateArtist.getArtist_name());
	artist.setArtist_profile(updateArtist.getArtist_profile());
	
	return "artist/artist-update";
	}
}
