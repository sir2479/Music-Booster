package com.example.mub.model.artist;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ArtistUpdateForm {
	private Long artist_id;
	private String artist_member_id;
	@NotNull
	private String artist_name;
	private String artist_profile;
	public boolean isFileRemoved; {
	}
}
