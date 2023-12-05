package com.example.model.board;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardWriteForm {
	@NotBlank
	private String board_title; //글 제목
	@NotBlank
	private String board_content; //내용
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setBoard_title(boardWriteForm.getBoard_title());
		board.setBoard_content(boardWriteForm.getBoard_content());
		return board;
	}
}
