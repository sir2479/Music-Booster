package com.example.mub.repository;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.wishlist.Wishlist;

@Mapper
public interface WishlistMapper {

	void saveWishlist(Wishlist wishlist);
	
	List<Wishlist> findWishlistByMemberId(String member_id);
	
	//void deleteWishlist(String member_id, Long music_id);
	
	void deleteWishlist(Wishlist wishlist);
}
