package com.example.mub.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.ImageFile;
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
		List<ImageFile> imageFiles = new ArrayList<>();
		
		// 장르 음악
		List<Music> genreMusics = musicService.findMusicByGenre("Ballad");
		
		if(genreMusics.size() > 5) {
			genreMusics = genreMusics.subList(0, 5);
		}
		
		for(int i = 0 ; i < musics.size() ; i++ ) {
			musicFiles.add(musicService.findMusicFileByMusicId(musics.get(i).getMusic_id()));
			log.info("musicFiles: {}", musicFiles);
			musics.get(i).setMusic_file_saved_name(musicFiles.get(i).getFile_saved_name());
			
			imageFiles.add(musicService.findImageFileByMusicId(musics.get(i).getMusic_id()));
			log.info("imageFiles: {}", imageFiles);
			musics.get(i).setImage_file_saved_name(imageFiles.get(i).getFile_saved_name());
		}
		
		log.info("musics: {}", musics);
//		log.info("musicFiles: {}", musicFiles);
//		log.info("genreMusics: {}", genreMusics);
		
		model.addAttribute("musics", musics);
		model.addAttribute("musicFile", musicFiles);
		model.addAttribute("genreMusics", genreMusics);


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
    			@RequestParam MultipartFile musicFile,
    			@RequestParam(required = false) MultipartFile imageFile) {
		
		log.info("musicUploadForm: {}", musicUploadForm);
		log.info("musicFile: {}", musicFile);
		log.info("imageFile: {}", imageFile);
		
		Music music = musicUploadForm.toMusic(musicUploadForm);
		
		musicService.uploadMusic(music, musicFile, imageFile);
		

		return "redirect:/music/music-home";
    }
	
	@PostMapping("{music_genre}")
	public ResponseEntity<List<Music>> genreChange(@PathVariable String music_genre,
												Model model){
		
		log.info("genreChange 들어옴");
		log.info("music_genre: {}", music_genre);
		
		List<Music> genreMusics = musicService.findMusicByGenre(music_genre);
		List<ImageFile> imageFiles = new ArrayList<>();
		
		if(genreMusics.size() > 5) {
			genreMusics = genreMusics.subList(0, 5);
		}		
		
		for(int i = 0 ; i < genreMusics.size() ; i++ ) {
			imageFiles.add(musicService.findImageFileByMusicId(genreMusics.get(i).getMusic_id()));
			genreMusics.get(i).setImage_file_saved_name(imageFiles.get(i).getFile_saved_name());
		}
		
		log.info("스크립트 genreMusics: {}", genreMusics);
		
		//log.info("genreMusics: {}", genreMusics);
		
		//model.addAttribute("genreMusics", genreMusics);
		
		return ResponseEntity.ok(genreMusics);
	}
	
}
