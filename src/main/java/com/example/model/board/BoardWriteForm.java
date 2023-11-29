package com.example.model.board;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class BoardWriteForm {
	@NotBlank
	private String title; //글 제목
	@NotBlank
	private String contents; //내용
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setTitle(boardWriteForm.getTitle());
		board.setContents(boardWriteForm.getContents());
		return board;
	}
}
