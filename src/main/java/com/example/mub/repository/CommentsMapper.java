package com.example.mub.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.comments.Comments;

@Mapper
public interface CommentsMapper {
	//리플등록
	void saveComments(Comments comments);
	//리플읽기
	Comments findComments(Long comments_id);
	//리플목록
	List<Comments> findAllComments(Long comments_board);
	//리플수정
	void updateComments(Comments comments);
	//리플삭제
	void removeComments(Long comments_board);
}
