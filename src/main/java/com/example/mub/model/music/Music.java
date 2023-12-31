package com.example.mub.model.music;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Music {

	private Long music_id;
	private String music_name;
	private Long music_artist_id;
	private String music_genre;
	private String music_lyrics;
	private Long music_playcount;
	private Long music_like;
	
	// db에 저장 안하는 필드
	private String music_file_saved_name;
	private String image_file_saved_name;
	private String artist_name;
	private boolean wishlist;

}
