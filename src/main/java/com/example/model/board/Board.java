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
//		this.board_hit++;
//	}
	
	public static BoardUpdateForm toBoardUpdateForm(Board board) {
		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
		boardUpdateForm.setBoard_id(board.getBoard_id());
		boardUpdateForm.setBoard_title(board.getBoard_title());
		boardUpdateForm.setBoard_content(board.getBoard_content());
		boardUpdateForm.setBoard_member(board.getBoard_member());
		boardUpdateForm.setBoard_hit(board.getBoard_hit());
		boardUpdateForm.setBoard_create_time(board.getBoard_create_time());
		return boardUpdateForm;
	}
	
}
