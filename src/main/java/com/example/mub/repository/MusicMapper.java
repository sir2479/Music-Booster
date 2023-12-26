package com.example.mub.repository;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.music.Music;

@Mapper
public interface MusicMapper {

	void uploadMusic(Music music);
	
	List<Music> findAllMusics();
	
	Music findMusicByMusicId(Long Music_id);
	
	List<Music> findMusicByGenre(String music_genre);
	
	List<Music> findMusicsDescLike();
	
	void addLike(Long Music_id);
	
	void minusLike(Long Music_id);
}
