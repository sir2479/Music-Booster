package com.example.mub.model.wishlist;

import com.example.mub.model.music.Music;

import lombok.Data;

@Data
public class Wishlist {

	private Long wishlist_id;
	private String member_id;
	private Long music_id;
	
}
