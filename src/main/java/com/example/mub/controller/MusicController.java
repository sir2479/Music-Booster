package com.example.mub.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.member.Member;
import com.example.mub.model.artist.Artist;
import com.example.mub.model.file.ImageFile;
import com.example.mub.model.file.MusicFile;
import com.example.mub.model.music.Music;
import com.example.mub.model.music.MusicUploadForm;
import com.example.mub.model.wishlist.Wishlist;
import com.example.mub.repository.FileMapper;
import com.example.mub.repository.WishlistMapper;
import com.example.mub.service.ArtistService;
import com.example.mub.service.MusicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("music")
public class MusicController {
	
	private final MusicService musicService;
	private final ArtistService artistService;
	private final FileMapper fileMapper;
	private final WishlistMapper wishlistMapper;

	@GetMapping("music-home")
    public String musicHome(Model model,
    		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
    		@RequestParam(value="searchText", defaultValue = "") String searchText) {
		
		List<Music> musics = musicService.findAllMusic(searchText);
		List<Music> rankMusics = musicService.findMusicsDescLike();
				
		// 추천 음악
		// 랜덤 숫자 생성
		Random random = new Random();
		if(musics.size() != 0) {
			int randomNumber = random.nextInt(musics.size());
			
			// 랜덤 음악 추천
			Music recommendMusic = musicService.findMusicByMusicId(musics.get(randomNumber).getMusic_id());
			ImageFile reMusicImage = fileMapper.findImageFileByMusicId(recommendMusic.getMusic_id());
			recommendMusic.setImage_file_saved_name(reMusicImage.getFile_saved_name());
			model.addAttribute("recommendMusic", recommendMusic);
		}		
		
		
		// musics에 음악파일이랑 이미지파일 넣기 위한 변수
		List<MusicFile> musicFiles = new ArrayList<>();
		List<ImageFile> imageFiles = new ArrayList<>();		
		
		// musics 객체에 파일name값 넣어줌
		for(int i = 0 ; i < musics.size() ; i++) {
			musicFiles.add(musicService.findMusicFileByMusicId(musics.get(i).getMusic_id()));
			musics.get(i).setMusic_file_saved_name(musicFiles.get(i).getFile_saved_name());
			
			imageFiles.add(musicService.findImageFileByMusicId(musics.get(i).getMusic_id()));
			musics.get(i).setImage_file_saved_name(imageFiles.get(i).getFile_saved_name());
		}
		
		
		// 음악 랭킹
		List<ImageFile> rankImageFiles = new ArrayList<>();
		
		for(int i = 0 ; i < rankMusics.size() ; i++) {
			rankImageFiles.add(musicService.findImageFileByMusicId(rankMusics.get(i).getMusic_id()));
			rankMusics.get(i).setImage_file_saved_name(rankImageFiles.get(i).getFile_saved_name());
		}		
		if(rankImageFiles.size() > 6) {
			rankImageFiles = rankImageFiles.subList(0, 6);
		}	

		
		// 위시리스트 확인 (빈 하트인지 빨간 하트인지)
		if(loginMember != null) {
			List<Wishlist> findWishlist = wishlistMapper.findWishlistByMemberId(loginMember.getMember_id());
			log.info("findWishlist: {}", findWishlist);
			log.info("loginMember.getMember_id(): {}", loginMember.getMember_id());
			
			for (Music music : musics) {
		        for (Wishlist wishlist : findWishlist) {		        		        	
		            if (music != null && wishlist != null &&
		            	music.getMusic_id() != null && 
		            	wishlist.getMusic_id() != null &&
		            	music.getMusic_id().equals(wishlist.getMusic_id())) {
		            	
		                music.setWishlist(true);
		                break;
		            } else {
		            	music.setWishlist(false);
		            }
		        }
		        //log.info("musicWishlist: {}", music.isWishlist());
		    }
		}


//		log.info("musics: {}", musics);
//		log.info("musicFiles: {}", musicFiles);
//		log.info("rankMusic: {}", rankMusic);
		
		model.addAttribute("musics", musics);
		model.addAttribute("musicFile", musicFiles);
		model.addAttribute("rankMusics", rankMusics);


        return "music/music-home";
    }
	
	@GetMapping("upload")
    public String uploadForm(@RequestParam Long artist_id,
    						Model model) {
		
		Artist artist = artistService.findArtist(artist_id);
		MusicUploadForm musicUploadForm = new MusicUploadForm();
		musicUploadForm.setMusic_artist_id(artist.getArtist_id());
		musicUploadForm.setArtist_name(artist.getArtist_name());
		
		log.info("musicUploadForm: {}", musicUploadForm);
		model.addAttribute("uploadForm", musicUploadForm);

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
	
	// 음악 상세 페이지
	@GetMapping("music-read")
    public String musicRead(@RequestParam Long music_id, 
    						@SessionAttribute(name = "loginMember", required = false) Member loginMember,
    						Model model) {
		
		Music findMusic = musicService.findMusicByMusicId(music_id);
		MusicFile musicFile = musicService.findMusicFileByMusicId(music_id);
		ImageFile imageFile = musicService.findImageFileByMusicId(music_id);
		
		// 음악파일이랑 이미지파일 세이브Id 넣어줌
		findMusic.setMusic_file_saved_name(musicFile.getFile_saved_name());
		findMusic.setImage_file_saved_name(imageFile.getFile_saved_name());
		
		
		// 위시리스트 확인 (빈 하트일지 빨간 하트일지)
		if(loginMember != null) {
			List<Wishlist> findWishlist = wishlistMapper.findWishlistByMemberId(loginMember.getMember_id());
			
			for (Wishlist wishlist : findWishlist) {		        		        	
	            if (findMusic != null && wishlist != null &&
	            	findMusic.getMusic_id() != null && 
	            	wishlist.getMusic_id() != null &&
	            	findMusic.getMusic_id().equals(wishlist.getMusic_id())) {
	            	
	            	findMusic.setWishlist(true);
	                break;
	            } else {
	            	findMusic.setWishlist(false);
	            }
	        }
		}
		
		model.addAttribute("music", findMusic);

        return "music/music-read";
    }

	// 장르 음악 
	@PostMapping("{music_genre}")
	public ResponseEntity<List<Music>> genreChange(@PathVariable String music_genre){
		
		List<Music> genreMusics = musicService.findMusicByGenre(music_genre);
		List<ImageFile> imageFiles = new ArrayList<>();
		
		if(genreMusics.size() > 5) {
			genreMusics = genreMusics.subList(0, 5);
		}		
		
		for(int i = 0 ; i < genreMusics.size() ; i++ ) {
			imageFiles.add(musicService.findImageFileByMusicId(genreMusics.get(i).getMusic_id()));
			genreMusics.get(i).setImage_file_saved_name(imageFiles.get(i).getFile_saved_name());
		}
		
		return ResponseEntity.ok(genreMusics);
	}
	
	// 하트 채우기
	@GetMapping("fillHeart/{music_id}")
	public ResponseEntity<Wishlist> fillHeart(@PathVariable Long music_id,
					@SessionAttribute(name = "loginMember", required = false) Member loginMember){
		
		Wishlist wishlist = new Wishlist();
		wishlist.setMusic_id(music_id);
		wishlist.setMember_id(loginMember.getMember_id());
		
		musicService.fillHeart(wishlist);
		
		return ResponseEntity.ok(wishlist); // Wishlist형으로 굳이 안보내도 됨
	}
	
	// 하트 비우기
	@GetMapping("emptyHeart/{music_id}")
	public ResponseEntity<String> emptyHeart(@PathVariable Long music_id,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember){
		
		log.info("music_id: {}", music_id);
		log.info("loginMember: {}", loginMember.getMember_id());
		
		Wishlist wishlist = new Wishlist();
		wishlist.setMember_id(loginMember.getMember_id());
		wishlist.setMusic_id(music_id);
		
		musicService.emptyHeart(wishlist);

		return ResponseEntity.ok("wishlist 삭제 완료");
	}
	
	@GetMapping("my-music")
	public String myMusic(Model model,
				@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
			
		List<Music> musics = musicService.findMusicByWishlist(loginMember.getMember_id());
		List<MusicFile> musicFiles = new ArrayList<>();
		List<ImageFile> imageFiles = new ArrayList<>();
		
		// music 객체에 파일id값 넣어줌
		for(int i = 0 ; i < musics.size() ; i++) {
			musicFiles.add(musicService.findMusicFileByMusicId(musics.get(i).getMusic_id()));
			musics.get(i).setMusic_file_saved_name(musicFiles.get(i).getFile_saved_name());
					
			imageFiles.add(musicService.findImageFileByMusicId(musics.get(i).getMusic_id()));
			musics.get(i).setImage_file_saved_name(imageFiles.get(i).getFile_saved_name());
		}
		
		// 위시리스트 확인 (빈 하트일지 빨간 하트일지)
		if(loginMember != null) {
			List<Wishlist> findWishlist = wishlistMapper.findWishlistByMemberId(loginMember.getMember_id());
			
			for (Music music : musics) {
		        for (Wishlist wishlist : findWishlist) {		        	
		        	
		            if (music != null && wishlist != null &&
		            	music.getMusic_id() != null && 
		            	wishlist.getMusic_id() != null &&
		            	music.getMusic_id().equals(wishlist.getMusic_id())) {
		            	
		                music.setWishlist(true);
		                break;
		            } else {
		            	music.setWishlist(false);
		            }
		        }
		    }
		}
		
		log.info("musics: {}", musics);
		
		model.addAttribute("musics", musics);
		
		return "music/my-music";
	}
	
	
}
