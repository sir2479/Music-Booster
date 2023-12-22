//package com.example.mub.controller;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.SessionAttribute;
//
//import com.example.mub.model.comments.Comments;
//import com.example.mub.model.member.Member;
//import com.example.mub.repository.CommentsMapper;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//
//public class CommentsController {
//	@RestController
//	@RequestMapping("comments")
//	@RequiredArgsConstructor
////	@Slf4j
//	public class ReplyRestController {
//		
//		private final CommentsMapper commentsMapper;
//		
//		//리플 등록
//		@PostMapping("{board_id}") //	/reply/106
//		public ResponseEntity<String> writeReply(@ModelAttribute Comments comments,
//												@SessionAttribute("loginMember")Member loginMember,
//												@PathVariable Long board_id){
////			log.info("comments:{}", comments);
//			
//			comments.setComment_user(loginMember.getMember_id());
//			reply.setBoard_id(board_id);
//			
//			replyMapper.saveReply(reply);
//			
//			return ResponseEntity.ok("등록 성공");
//		}
//		
//		//리플 읽기
//		@GetMapping("{board_id}/{reply_id}") /*reply/106/152*/
//		public ResponseEntity<Reply> findReply(@PathVariable Long board_id,
//												@PathVariable Long reply_id){
//			Reply reply = replyMapper.findReply(reply_id);
//			return ResponseEntity.ok(reply);
//		}
//		
//		//리플목록
//		@GetMapping("{board_id}")
//		public ResponseEntity<List<Reply>> findReplies(@PathVariable Long board_id){
//			List<Reply> replies = replyMapper.findReplies(board_id);
//			return ResponseEntity.ok(replies);
//		}
//		
//		//리플 수정
//		@PutMapping("{board_id}/{reply_id}")
//		public ResponseEntity<Reply> updateReply(@SessionAttribute("loginMember") Member loginMember,
//												@PathVariable Long board_id,
//												@PathVariable Long reply_id,
//												@ModelAttribute Reply reply){
//			//수정권한이 있는지 체크
//			
//			//리플 수정
//			return ResponseEntity.ok(reply);
//		}
//		
//		//리플 삭제
//		@DeleteMapping("{board_id}/{reply_id}")
//		public ResponseEntity<String> removeReply(@SessionAttribute("loginMember") Member loginMebmer,
//													@PathVariable Long board_id,
//													@PathVariable Long reply_id){
//			//삭제 권한이 있는지 체크
//			return ResponseEntity.ok("삭제 성공");
//		}
//	}
//
//}
