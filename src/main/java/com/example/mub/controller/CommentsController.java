package com.example.mub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.mub.model.comments.Comments;
import com.example.mub.model.member.Member;
import com.example.mub.repository.CommentsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
@Slf4j
public class CommentsController {
		
		private final CommentsMapper commentsMapper;
		
		//리플 등록
		@PostMapping("{comments_board}") //	/reply/106
		public ResponseEntity<String> writeComments(@ModelAttribute Comments comments,
												@SessionAttribute("loginMember")Member loginMember,
												@PathVariable("comments_board") Long comments_board){
			log.info("comments:{}", comments);
			
			comments.setComments_member(loginMember.getMember_id());
			comments.setComments_board(comments_board);
			
			commentsMapper.saveComments(comments);
			
			return ResponseEntity.ok("등록 성공");
		}
		
		//리플 읽기
		@GetMapping("{comments_board}/{comments_id}") /*reply/106/152*/
		public ResponseEntity<Comments> findComments(@PathVariable("comments_board") Long comments_board,
												@PathVariable Long comments_id){
			Comments comments = commentsMapper.findComments(comments_id);
			return ResponseEntity.ok(comments);
		}
		
		//리플목록
		@GetMapping("{comments_board}")
		public ResponseEntity<List<Comments>> findAllComments(@PathVariable("comments_board") Long comments_board){
			List<Comments> comments = commentsMapper.findAllComments(comments_board);
			return ResponseEntity.ok(comments);
		}
		
		//리플 수정
		@PutMapping("{comments_board}/{comments_id}")
		public ResponseEntity<Comments> updateComments(@SessionAttribute("loginMember") Member loginMember,
												@PathVariable Long comments_board,
												@PathVariable Long comments_id,
												@ModelAttribute Comments comments){
			//수정권한이 있는지 체크
			Comments findComments = commentsMapper.findComments(comments_id);
			findComments.setComments_content(comments.getComments_content());
			
			commentsMapper.updateComments(findComments);
			//리플 수정
			return ResponseEntity.ok(comments);
		}
		
		//리플 삭제
		@DeleteMapping("{comments_board}/{comments_id}")
		public ResponseEntity<String> removeComments(@SessionAttribute("loginMember") Member loginMebmer,
													@PathVariable Long comments_board,
													@PathVariable Long comments_id,
													@ModelAttribute Comments comments){
			//삭제 권한이 있는지 체크
			Comments findComments = commentsMapper.findComments(comments_id);
			findComments.setComments_id(comments.getComments_id());
			commentsMapper.removeComments(null);
			return ResponseEntity.ok("삭제 성공");
		}
	}


