package com.example.mub.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MusicFile {
	private Long file_id;
	private String file_original_name;
	private String file_saved_name;
	private Long file_size;
	private Long file_music_id;
	private String file_path;
	
	public MusicFile(AttachedFile attachedFile, Long file_music_id) {
		this.file_original_name = attachedFile.getFile_original_name();
		this.file_saved_name = attachedFile.getFile_saved_name();
		this.file_size = attachedFile.getFile_size();
		this.file_music_id = file_music_id;
	}
}
