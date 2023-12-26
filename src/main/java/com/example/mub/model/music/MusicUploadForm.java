package com.example.mub.model.music;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MusicUploadForm {

	private String music_name;
	private Long music_artist_id;
	private String music_genre;
	private String music_lyrics;
	private String artist_name;
	
	public Music toMusic(MusicUploadForm musicUploadForm) {
		Music music = new Music();
		music.setMusic_name(musicUploadForm.getMusic_name());
		music.setMusic_artist_id(musicUploadForm.getMusic_artist_id());
		music.setMusic_genre(musicUploadForm.getMusic_genre());
		music.setMusic_lyrics(musicUploadForm.getMusic_lyrics());	
		music.setArtist_name(musicUploadForm.getArtist_name());
		return music;
	}

}
