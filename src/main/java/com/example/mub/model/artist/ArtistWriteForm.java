package com.example.mub.model.artist;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ArtistWriteForm {
	private Long artist_id;
	private String artist_member_id;
	@NotNull
	private String artist_name;
	private String artist_profile;
	//private String imagefile_saved_name;
	
	public static Artist toArtist(ArtistWriteForm artistWriteForm) {
		Artist artist = new Artist();
		artist.setArtist_name(artistWriteForm.getArtist_name());
		artist.setArtist_profile(artistWriteForm.getArtist_profile());
		//artist.setImagefile_saved_name(artistWriteForm.getImagefile_saved_name());
		return artist;
	}

}
