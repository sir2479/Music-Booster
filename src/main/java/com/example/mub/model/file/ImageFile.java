package com.example.mub.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageFile {
	private Long file_id;
	private String file_original_name;
	private String file_saved_name;
	private Long file_size;
	private Long file_member_id;
	private Long file_board_id;
	private Long file_artist_id;
 	private Long file_music_id;
	
	
	/*
	 * 멤버아이디,
	 * 보드아이디,
	 * 아티스트아이디,
	 * 뮤직아이디
	 */
	
	public ImageFile(AttachedFile attachedFile) {
		//super();
		this.file_original_name = attachedFile.getFile_original_name();
		this.file_saved_name = attachedFile.getFile_saved_name();
		this.file_size = attachedFile.getFile_size();		
	}
	
}
