package com.example.mub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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

import com.example.mub.model.artist.Artist;
import com.example.mub.model.artist.ArtistUpdateForm;
import com.example.mub.model.artist.ArtistWriteForm;
import com.example.mub.model.file.ImageFile;
import com.example.mub.model.member.Member;
import com.example.mub.model.music.Music;
import com.example.mub.repository.ArtistMapper;
import com.example.mub.service.ArtistService;
import com.example.mub.service.MemberService;
import com.example.mub.service.MusicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {
	
	private final ArtistService artistService;
	private final ArtistMapper artistMapper;
	private final MusicService musicService;
	private final MemberService memberService;
	@Value("${file.upload.path}")
    private String uploadPath;
	
	// 아티스트 페이지 이동
	@GetMapping("artist-home")
	public String artist(@RequestParam(value="search", defaultValue="")String search,
						 Model model) {
		
		//int total = artistService.getTotal(search);
		
		List<Artist> artists = artistMapper.findAllArtists(search);
		List<ImageFile> imageFile = new ArrayList<>();
		
		for(int i = 0 ; i < artists.size() ; i++ ) {
			imageFile.add(artistService.findImageFileByArtistId(artists.get(i).getArtist_id()));
			log.info("imageFiles: {}", imageFile);
			artists.get(i).setImagefile_saved_name(imageFile.get(i).getFile_saved_name());
		}
		
		model.addAttribute("artists", artists);
		model.addAttribute("imageFile",imageFile);
		
		return "artist/artist-home"; 
	}
	
	@GetMapping("artist-write")
	public String writeForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
							Model model) {
		
		model.addAttribute("artistWriteForm", new ArtistWriteForm());
		model.addAttribute("imageFile", new ImageFile());
		
		return "artist/artist-write";
	}
	
	@PostMapping("artist-write")
	public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
            			@Validated @ModelAttribute("artistWriteForm") ArtistWriteForm artistWriteForm,
            			@RequestParam(required = false) MultipartFile file
            			,BindingResult result) {

		if (result.hasErrors()) {
			return "artist/artist-write";
		}		
		
		Artist artist = ArtistWriteForm.toArtist(artistWriteForm);
		
		artist.setArtist_member_id(loginMember.getMember_id());
		
		artistService.saveArtist(artist, file);
		
		return "redirect:/artist/artist-home";
	}
	
	@GetMapping("artist-read")
	public String read(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
					   @RequestParam Long artist_id,
					   Model model) {

		Artist artist = artistService.readArtist(artist_id);
		ImageFile imageFile = artistService.findImageFileByArtistId(artist_id);
		List<Music> music = musicService.findMusicByArtistId(artist_id);
		List<ImageFile> imageFiles = new ArrayList<>();
		Member member = memberService.findMember(artist.getArtist_member_id());	
		
		for(int i = 0 ; i < music.size() ; i++) {		
			imageFiles.add(musicService.findImageFileByMusicId(music.get(i).getMusic_id()));
			music.get(i).setImage_file_saved_name(imageFiles.get(i).getFile_saved_name());
		}
		
		if (artist == null) {
			log.info("게시글 없음");
			return "redirect:/artist/artist-home";
		}
		
		model.addAttribute("music", music);
		model.addAttribute("imageFile",imageFile);
		model.addAttribute("artist", artist);
		model.addAttribute("member", member);
	
		return "artist/artist-read";
	}
	
	@GetMapping("artist-update")
	public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
							 @RequestParam Long artist_id,
							 Model model) {

		Artist artist = artistService.findArtist(artist_id);
		if (artist == null || !artist.getArtist_member_id().equals(loginMember.getMember_id())) {
			log.info("수정 권한 없음");
			return "redirect:/artist/artist-home";
		}
		
		model.addAttribute("artist", Artist.toArtistUpdateForm(artist));
		ImageFile imageFile = artistService.findImageFileByArtistId(artist_id);
		
		model.addAttribute("imageFile",imageFile);

		return "artist/artist-update";
	}

			
	@PostMapping("artist-update")
	public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
            @RequestParam Long artist_id,
            @Validated @ModelAttribute("artist") ArtistUpdateForm updateArtist,
            @RequestParam(required = false) MultipartFile file,
            BindingResult result) {

		if (result.hasErrors()) {
			return "artist/artist-update";
		}
		
		Artist artist = artistService.findArtist(artist_id);
	
		if (artist == null || !artist.getArtist_member_id().equals(loginMember.getMember_id())) {
			log.info("권한 없음");
			return "redirect:/artist/artist-update";
		}

		artist.setArtist_name(updateArtist.getArtist_name());
		artist.setArtist_profile(updateArtist.getArtist_profile());
		
		artistService.updateArtist(artist, updateArtist.isFileRemoved() , file);
	
		return "redirect:/artist/artist-home";
	}
	
	@GetMapping("artist-remove")
	public String remove(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
						 @RequestParam Long artist_id) {
	
		Artist artist = artistService.findArtist(artist_id);
		
		if (artist == null || !artist.getArtist_member_id().equals(loginMember.getMember_id())) {
            log.info("삭제 권한 없음");
            return "redirect:/artist/artist-home";
        }
		
		ImageFile imageFile = artistService.findImageFileByArtistId(artist_id);
		
		
		
		if (imageFile != null) {
			artistService.removeImageFile(imageFile.getFile_artist_id());
		}	
		
		artistMapper.removeArtist(artist_id);		
		
		return "redirect:/artist/artist-home";
	}
}
