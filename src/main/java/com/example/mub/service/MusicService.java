package com.example.mub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.*;
import com.example.mub.model.music.Music;
import com.example.mub.repository.FileMapper;
import com.example.mub.repository.MemberMapper;
import com.example.mub.repository.MusicMapper;
import com.example.mub.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicService {
	
	private final MusicMapper musicMapper;
	private final FileMapper filemapper;
	private final FileService fileService;
	
	@Transactional
	public void uploadMusic(Music music, MultipartFile mFile, MultipartFile iFile) {
		
		log.info("서비스에 온 뮤직: {}", music);
		
		//데이터베이스에 저장
		musicMapper.uploadMusic(music);	
		
		log.info("서비스에 온 뮤직 메퍼 후: {}", music);
		
		// 음악파일 저장
		if(mFile != null && mFile.getSize() > 0) {
			AttachedFile attachedFile = fileService.saveFile(mFile);
			MusicFile musicFile = new MusicFile(attachedFile, music.getMusic_id());
			
			log.info("서비스 musicFile: {}", musicFile);
			// 첨부파일 내용을 데이터베이스에 저장
			filemapper.musicFileUpload(musicFile);
			
		}
		
		if(iFile != null && iFile.getSize() > 0) {
			AttachedFile attachedFile = fileService.saveFile(iFile);
			ImageFile imageFile = new ImageFile(attachedFile);
			imageFile.setFile_music_id(music.getMusic_id());
			log.info("서비스 imageFile: {}", imageFile);
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
	
	
	


}
