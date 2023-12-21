package com.example.mub.model.board;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardWriteForm {
	
	private BoardCategory board_category;

	@NotBlank(message="글이 없습니다.")
	private String board_title; //글 제목

	private String board_member;	// 작성자

	@NotBlank(message="내용이없습니다.")
	private String board_content; //내용	
	
	private String board_create_time;	//작성일
	
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setBoard_category(boardWriteForm.getBoard_category());
		board.setBoard_title(boardWriteForm.getBoard_title());
		board.setBoard_content(boardWriteForm.getBoard_content());
		return board;
	}
}
