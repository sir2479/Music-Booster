package com.example.mub.model.board;

public enum BoardCategory {
	NOTICE("공지 게시판"),
	NEWS("새 소식"),
	BALLAD("발라드"),
	DANCE("댄스"),
	RAP_HIPHOP("랩/힙합");
	
	private String description;
	
	private BoardCategory(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}	

}
