package com.example.mub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.artist.Artist;
import com.example.mub.model.file.AttachedFile;
import com.example.mub.model.file.ImageFile;
import com.example.mub.model.file.MusicFile;
import com.example.mub.repository.ArtistMapper;
import com.example.mub.repository.FileMapper;
import com.example.mub.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArtistService {
	
	private final ArtistMapper artistMapper;
	private final FileService fileService;
	private final FileMapper fileMapper;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Transactional
	public void saveArtist(Artist artist, MultipartFile file) {
		
		log.info("서비스에 온 뮤직: {}", file);
		artistMapper.saveArtist(artist);
		if(file != null && file.getSize() > 0) {
			AttachedFile attachedFile  = fileService.saveFile(file);
			ImageFile imageFile = new ImageFile(attachedFile);
			imageFile.setFile_artist_id(artist.getArtist_id());
			fileMapper.imageFileUpload(imageFile);
		} 
		log.info("saveArtist맵퍼통과");
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
		//updateArtist(artist);
		return artist;
	}
	
	@Transactional
	public void updateArtist(Artist updateArtist, boolean isFileRemoved, MultipartFile file) {
		Artist artist = artistMapper.findArtist(updateArtist.getArtist_id());
		
		if(artist != null) {
			artistMapper.updateArtist(updateArtist);
			ImageFile imageFile = fileMapper.findImageFileByArtistId(updateArtist.getArtist_id());
			if(imageFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				fileMapper.removeImageFileByArtistId(imageFile.getFile_artist_id());
			}
			
		}
		
		if(file != null && file.getSize() > 0) {
			AttachedFile attachedFile  = fileService.saveFile(file);
			ImageFile imageSavedFile = new ImageFile(attachedFile);
			imageSavedFile.setFile_artist_id(updateArtist.getArtist_id());
			fileMapper.imageFileUpload(imageSavedFile);
		} 
	}
	
	public ImageFile findImageFileByArtistId(Long artist_id) {
		return fileMapper.findImageFileByArtistId(artist_id);
	}
	
	@Transactional
	public void removeImageFile(Long artist_id) {
		ImageFile imageFile = fileMapper.findImageFileByArtistId(artist_id);
		if(imageFile != null) {
			String fullPath = uploadPath + "/" + imageFile.getFile_saved_name();
			fileService.deleteFile(fullPath);
			fileMapper.removeImageFileByArtistId(imageFile.getFile_artist_id());
		}
	}
	
	

}
