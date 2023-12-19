package com.example.mub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mub.model.artist.Artist;
import com.example.mub.repository.ArtistMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArtistService {
	
	private final ArtistMapper artistMapper;
	
	@Transactional
	public void saveArtist(Artist artist) {
		artistMapper.saveArtist(artist);
	}
	
	public List<Artist> findAllArtists(String search) {
		return artistMapper.findAllArtists(search);
	}
	
	public Artist artistfind(Long artist_id) {		
		return artistMapper.findArtistByName(artist_id);
	}
	
/*	public int getTotal(String search) {
		return artistMapper.getTotal(search);
	}
*/	
	public Artist findArtist(Long artist_id) {
		return artistMapper.findArtist(artist_id);
	}
	
	public Artist readArtist(Long artist_id) {
		Artist artist = findArtist(artist_id);
		updateArtist(artist);
		return artist;
	}
	
	@Transactional
	public void updateArtist(Artist updateArtist) {
		Artist artist = artistMapper.findArtist(updateArtist.getArtist_id());
		
		if(artist != null) {
			artistMapper.updateArtist(updateArtist);
		}
	}

}
