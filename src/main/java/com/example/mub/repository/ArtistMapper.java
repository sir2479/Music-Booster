package com.example.mub.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.artist.Artist;
import com.example.mub.model.file.ImageFile;

@Mapper
public interface ArtistMapper {

	List<Artist> findAllArtists(String search);
	Artist findArtistByName(Long artist_id);
	void updateArtist(Artist updateArtist);
	void removeArtist(Long artist_id);
	void saveArtist(Artist artist);
	Artist findArtist(Long artist_id);
//	int getTotal(String search);
	
}
