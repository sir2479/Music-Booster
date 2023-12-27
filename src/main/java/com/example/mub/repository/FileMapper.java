package com.example.mub.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.file.ImageFile;
import com.example.mub.model.file.MusicFile;

@Mapper
public interface FileMapper {

	void musicFileUpload(MusicFile file);
	
	MusicFile findMusicFileByMusicId(Long file_music_id);
	
	void imageFileUpload(ImageFile file);
	
	ImageFile findImageFileByMemberId(String file_member_id);
	
	ImageFile findImageFileByArtistId(Long file_artist_id);
	
	ImageFile findImageFileByMusicId(Long file_music_id);
	
	void removeImageFileByMemberId(String member_id);
	
	void removeImageFileByArtistId(Long file_artist_id);
}
