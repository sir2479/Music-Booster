package com.example.mub.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachedFile {
	private Long file_id;
	private String file_original_name;
	private String file_saved_name;
	private Long file_size;
	
	public AttachedFile(String file_original_name, String file_saved_name, Long file_size) {
		//super();
		this.file_original_name = file_original_name;
		this.file_saved_name = file_saved_name;
		this.file_size = file_size;
	}
}
