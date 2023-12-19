package com.example.mub.model.artist;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Artist {
	private Long artist_id;
	private String artist_member_id;
	private String artist_name;
	private String artist_profile;
	
	
	public static ArtistUpdateForm toArtistUpdateForm(Artist artist) {
		ArtistUpdateForm artistUpdateForm = new ArtistUpdateForm();
		artistUpdateForm.setArtist_id(artist.getArtist_id());
		artistUpdateForm.setArtist_member_id(artist.getArtist_member_id());
		artistUpdateForm.setArtist_name(artist.getArtist_name());
		artistUpdateForm.setArtist_profile(artist.getArtist_profile());
		return artistUpdateForm;
	}
}
