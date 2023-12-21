package com.example.mub.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.MusicFile;
import com.example.mub.model.music.Music;
import com.example.mub.model.music.MusicUploadForm;
import com.example.mub.service.MusicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("music")
public class MusicController {
	
	private final MusicService musicService;

	@GetMapping("music-home")
    public String music(Model model) {
		
		List<Music> musics = musicService.findAllMusic();
		
		List<MusicFile> musicFiles = new ArrayList<>();
		
		for(int i = 0 ; i < musics.size() ; i++ ) {
			musicFiles.add(musicService.findMusicFileByMusicId(musics.get(i).getMusic_id()));
		}
		
		
//		Music music = musicService.findMusicByMusicId(5L);
//		log.info("music: {}", music);
//		
//		MusicFile musicFile = musicService.findMusicFileByMusicId(music.getMusic_id());
//		log.info("musicFile: {}", musicFile);
		
		log.info("musics: {}", musics);
		log.info("musicFiles: {}", musicFiles);
		
		model.addAttribute("musics", musics);
		model.addAttribute("musicFiles", musicFiles);


        return "music/music-home";
    }
	
	@GetMapping("upload")
    public String uploadForm(Model model) {
		
		model.addAttribute("uploadForm", new MusicUploadForm());

        return "music/upload";
    }
	
	@PostMapping("upload")
    public String upload(
    			@Validated @ModelAttribute("uploadForm") MusicUploadForm musicUploadForm,
    			@RequestParam MultipartFile file) {
		
		log.info("musicUploadForm: {}", musicUploadForm);
		log.info("file: {}", file);
		
		Music music = musicUploadForm.toMusic(musicUploadForm);
		
		musicService.uploadMusic(music, file);
		

		return "redirect:/music/music-home";
    }
}
