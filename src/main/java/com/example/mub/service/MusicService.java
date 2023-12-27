package com.example.mub.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.*;
import com.example.mub.model.music.Music;
import com.example.mub.model.wishlist.Wishlist;
import com.example.mub.repository.FileMapper;
import com.example.mub.repository.MusicMapper;
import com.example.mub.repository.WishlistMapper;
import com.example.mub.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicService {
	
	private final MusicMapper musicMapper;
	private final FileMapper filemapper;
	private final WishlistMapper wishlistMapper;
	private final FileService fileService;
	
	@Transactional
	public void uploadMusic(Music music, MultipartFile mFile, MultipartFile iFile) {
		
		//데이터베이스에 저장
		musicMapper.uploadMusic(music);	
		
		// 음악파일 저장
		if(mFile != null && mFile.getSize() > 0) {
			AttachedFile attachedFile = fileService.saveFile(mFile);
			MusicFile musicFile = new MusicFile(attachedFile, music.getMusic_id());
			// 첨부파일 내용을 데이터베이스에 저장
			filemapper.musicFileUpload(musicFile);			
		}
		
		if(iFile != null && iFile.getSize() > 0) {
			AttachedFile attachedFile = fileService.saveFile(iFile);
			ImageFile imageFile = new ImageFile(attachedFile);
			imageFile.setFile_music_id(music.getMusic_id());
			// 첨부파일 내용을 데이터베이스에 저장
			filemapper.imageFileUpload(imageFile);
		}			
	}
	
	public List<Music> findAllMusic(){
		
		return musicMapper.findAllMusics();
	}
	
	public Music findMusicByMusicId(Long music_id) {
		
		return musicMapper.findMusicByMusicId(music_id);
	}
	
	public List<Music> findMusicByGenre(String music_genre) {
		
		return musicMapper.findMusicByGenre(music_genre);
	}
	
	public MusicFile findMusicFileByMusicId(Long file_music_id) {
		
		return filemapper.findMusicFileByMusicId(file_music_id);
	}
	
	public ImageFile findImageFileByMusicId(Long file_music_id) {
		
		return filemapper.findImageFileByMusicId(file_music_id);
	}

	public List<Music> findMusicsDescLike(){
		
		return musicMapper.findMusicsDescLike();
	}
	
	@Transactional
	public void fillHeart(Wishlist wishlist) {
		wishlistMapper.saveWishlist(wishlist);
		musicMapper.addLike(wishlist.getMusic_id());
	}
	
	@Transactional
	public void emptyHeart(Wishlist wishlist) {
		wishlistMapper.deleteWishlist(wishlist);
		musicMapper.minusLike(wishlist.getMusic_id());
	}
	
	// 좋아요 한 음악 찾기
	public List<Music> findMusicByWishlist(String member_id){
		
		List<Music> wishlistMusics = new ArrayList<>();
		List<Wishlist> wishlists = wishlistMapper.findWishlistByMemberId(member_id);
		
		for(int i = 0 ; i < wishlists.size() ; i++) {			
			Music findMusic = musicMapper.findMusicByMusicId(wishlists.get(i).getMusic_id());
			wishlistMusics.add(findMusic);
		}		
		return wishlistMusics;		
	}

	public List<Music> findMusicByArtistId(Long artist_id) {
		
		return musicMapper.findMusicByArtistId(artist_id);
	}


}

	