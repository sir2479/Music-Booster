package com.example.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	private Long board_id; //게시물 아이디
	private String board_category;	// 카테고리
	private String board_title; //글 제목
	private String board_member; //작성자
	private String board_content; //내용
	private LocalDateTime board_create_time; //작성일
	private LocalDateTime board_edit_time;	// 수정일
	private Long board_hit; //조회수
	
	
	
//	public void addHit() {
//		this.hit++;
//	}
	
//	public static BoardUpdateForm toBoardUpdateForm(Board board) {
//		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
//		boardUpdateForm.setBoard_id(board.getBoard_id());
//		boardUpdateForm.setTitle(board.getTitle());
//		boardUpdateForm.setContents(board.getContents());
//		boardUpdateForm.setMember_id(board.getMember_id());
//		boardUpdateForm.setHit(board.getHit());
//		boardUpdateForm.setCreated_time(board.getCreated_time());
//		return boardUpdateForm;
//	}
	
}
