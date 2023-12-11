package com.example.mub.model.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachedFile {
	private long file_id;
	private long file_board_id;
	private String file_original_name;	//파일 원본 이름
	private String file_saved_name;	//파일 저장 이름
	private long file_size;	//파일 사이즈
	
	


	
	
}
