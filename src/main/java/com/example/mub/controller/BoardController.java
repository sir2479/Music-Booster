package com.example.mub.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.mub.model.board.Board;
import com.example.mub.model.board.BoardUpdateForm;
import com.example.mub.model.board.BoardWriteForm;
import com.example.mub.model.member.Member;
import com.example.mub.repository.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
	
    // 데이터베이스 접근을 위한 BoardMapper 필드 선언
    private final BoardMapper boardMapper;

    

    
    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                            Model model) {
//        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/member/login";
        }
//         writeForm.html의 필드 표시를 위해 빈 BoardWriteForm 객체를 생성하여 model 에 저장한다.
        model.addAttribute("writeForm", new BoardWriteForm());
        log.info("model: {}", model);
               
//         board/writeForm.html 을 찾아 리턴한다.
        return "board/write";
    }

//    // 게시글 쓰기
    @PostMapping("write")
    public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                        @Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm,
                        BindingResult result) {
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
//        if (loginMember == null) {
//            return "redirect:/member/login";
//        }

        log.info("board: {}", boardWriteForm);
        // validation 에러가 있으면 board/write.html 페이지를 다시 보여준다.
        if (result.hasErrors()) {
            return "board/write";
        }

        // 파라미터로 받은 BoardWriteForm 객체를 Board 타입으로 변환한다.
        Board board = BoardWriteForm.toBoard(boardWriteForm);
        
//        board.setBoard_member("abcd");
//        
        board.setBoard_category("notice");
        
        // board 객체에 로그인한 사용자의 아이디를 추가한다.
        board.setBoard_member(loginMember.getMember_id());
        // 데이터베이스에 저장한다.
        boardMapper.saveBoard(board);
        // board/list 로 리다이렉트한다.
        return "redirect:/board/list";
    }
    
	
	@GetMapping("list")
	public String board(@SessionAttribute(value = "loginMember", required = false)  Member loginMember,
            Model model) {
	      
		log.info("보드메퍼: {}", boardMapper.findAllBoards());
		// 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
		List<Board> boards = boardMapper.findAllBoards();
		// Board 리스트를 model 에 저장한다.
		model.addAttribute("boards", boards);

		// board/list.html 를 찾아서 리턴한다.
		return "board/list";
		}
	
    // 게시글 읽기
    @GetMapping("read")
    public String read(@SessionAttribute(value = "loginMember", required = false)  Member loginMember,
                       @RequestParam Long board_id,
                       Model model) {
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
//        if (loginMember == null) {
//            return "redirect:/member/login";
//        }

        log.info("id: {}", board_id);

        // board_id 에 해당하는 게시글을 데이터베이스에서 찾는다.
        Board board = boardMapper.findBoard(board_id);
        
        log.info("board: {}", board);
        
         //board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
        if (board == null) {
            log.info("게시글 없음");
            return "redirect:/board/list";
        }

        // 조회수 1 증가
        board.addHit();
        // 조회수를 증가하여 데이터베이스에 업데이트 한다.
        boardMapper.updateBoard(board);
        // 모델에 Board 객체를 저장한다.
        model.addAttribute("board", board);
        // board/read.html 를 찾아서 리턴한다.
        return "board/read";
    }
	
    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                             @RequestParam Long board_id,
                             Model model) {
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        log.info("id: {}", board_id);

        // board_id에 해당하는 게시글이 없거나 게시글의 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        Board board = boardMapper.findBoard(board_id);
        if (board == null || !board.getBoard_member().equals(loginMember.getMember_id())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }
        // model 에 board 객체를 저장한다.
        model.addAttribute("board", board);
//         board/update.html 를 찾아서 리턴한다.
        return "board/update";
    }

    // 게시글 수정
    @PostMapping("update")
    public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                         @RequestParam Long board_id,
                         @Validated @ModelAttribute("board") BoardUpdateForm updateBoard,
                         BindingResult result) {
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/member/login";
        }
//
        log.info("board: {}", updateBoard);
        // validation 에 에러가 있으면 board/update.html 페이지로 돌아간다.
        if (result.hasErrors()) {
            return "board/update";
        }

        // board_id 에 해당하는 Board 정보를 데이터베이스에서 가져온다.
        Board board = boardMapper.findBoard(board_id);
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getBoard_member().equals(loginMember.getMember_id())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }
        // 제목을 수정한다.
        board.setBoard_title(updateBoard.getBoard_title());
        // 내용을 수정한다.
        board.setBoard_content(updateBoard.getBoard_content());
        // 수정한 Board 를 데이터베이스에 update 한다.
        boardMapper.updateBoard(board);
        // 수정이 완료되면 리스트로 리다이렉트 시킨다.
        return "redirect:/board/list";
    }

    // 게시글 삭제
    @GetMapping("delete")
    public String remove(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                         @RequestParam Long board_id) {
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        // board_id 에 해당하는 게시글을 가져온다.
        Board board = boardMapper.findBoard(board_id);
        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (board == null || !board.getBoard_member().equals(loginMember.getMember_id())) {
            log.info("삭제 권한 없음");
            return "redirect:/board/list";
        }
        // 게시글을 삭제한다.
        boardMapper.removeBoard(board_id);
        // board/list 로 리다이렉트 한다.
        return "redirect:/board/list";
    }
    
	
}
