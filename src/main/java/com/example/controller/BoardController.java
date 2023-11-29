package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


import com.example.model.board.Board;
import com.example.model.board.BoardWriteForm;
import com.example.repository.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
public class BoardController {

	
    // 데이터베이스 접근을 위한 BoardMapper 필드 선언
    private final BoardMapper boardMapper;

    // BoardMapper 객체 필드 주입(생성자 주입 방식)
    public BoardController(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }
    
    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                            Model model) {
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        // writeForm.html의 필드 표시를 위해 빈 BoardWriteForm 객체를 생성하여 model 에 저장한다.
        model.addAttribute("writeForm", new BoardWriteForm());
        // board/writeForm.html 을 찾아 리턴한다.
        return "board/write";
    }

    // 게시글 쓰기
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
        // board 객체에 로그인한 사용자의 아이디를 추가한다.
        board.setMember_id(loginMember.getMember_id());
        // 데이터베이스에 저장한다.
        boardMapper.saveBoard(board);
        // board/list 로 리다이렉트한다.
        return "redirect:/board/list";
    }
    
	
	@GetMapping("/list")
	public String board(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
            Model model) {
	      
		// 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
		List<Board> boards = boardMapper.findAllBoards();
		// Board 리스트를 model 에 저장한다.
		model.addAttribute("boards", boards);
		// board/list.html 를 찾아서 리턴한다.
		return "board/list";
		}
}
