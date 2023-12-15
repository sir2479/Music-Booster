package com.example.mub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.AttachedFile;
import com.example.mub.model.file.MusicFile;
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
	public void uploadMusic(Music music, MultipartFile file) {
		
		log.info("서비스에 온 뮤직: {}", music);
		
		//데이터베이스에 저장
		musicMapper.uploadMusic(music);
		
		//첨부파일 저장
		if(file != null && file.getSize() > 0) {
			AttachedFile attachedFile = fileService.saveFile(file);
			MusicFile musicFile = new MusicFile(attachedFile, music.getMusic_id());
			
			log.info("musicFile: {}", musicFile);
			
			//첨부파일 내용을 데이터베이스에 저장
			filemapper.MusicFileUpload(musicFile);
		}
		
		
	}
	
	public List<Music> findAllMusic(){
		
		return musicMapper.findAllMusics();
	}
	
	public Music findMusicByMusicId(Long music_id) {
		
		return musicMapper.findMusicByMusicId(music_id);
	}
	
	public MusicFile findMusicFileByMusicId(Long file_music_id) {
		
		return filemapper.findMusicFileByMusicId(file_music_id);
	}
	


}
