package com.example.mub.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.file.MusicFile;

@Mapper
public interface FileMapper {

	void MusicFileUpload(MusicFile file);
	
	MusicFile findMusicFileByMusicId(Long file_music_id);
	
}
