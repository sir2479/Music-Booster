package com.example.mub.model.board;

import lombok.Data;


import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;



@Data
public class BoardUpdateForm {
    private Long board_id;
    private BoardCategory board_category;
    @NotBlank(message = "제목을 입력해 주세요.")
    private String board_title;
    private String board_member;
    @NotBlank(message="내용을 입력해 주세요")
    private String board_content;
    private LocalDateTime board_create_time;
    private LocalDateTime board_edit_time;
    private Long board_hit;
   
	
    
}
