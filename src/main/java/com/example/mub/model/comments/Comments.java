package com.example.mub.model.comments;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comments {
	private Long comments_id;	//리플 아이디(일련번호)
	private Long comments_board;	//게시글 아이디
	private String comments_member;	//작성자 아이디
	private String comments_content;	//리플 내용
	private LocalDateTime comments_create_time;	//등록시간
	private LocalDateTime comments_edit_time;	//수정시간
}
