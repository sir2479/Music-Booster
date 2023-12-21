package com.example.mub.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.board.Board;
import com.example.mub.model.board.BoardCategory;
import com.example.mub.repository.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)	// 다른곳에서 정상적으로 insert되더라도 다른곳에서 하나라도 에러가 있으면 모두 에러 처리
public class BoardService {
	
	private final BoardMapper boardMapper;
//	private final FileService fileService;
//    @Value("${file.upload.path}")
//    private String uploadPath;
	
	//write
	@Transactional
	public void saveBoard(Board board) {
        // 데이터베이스에 저장한다.
		log.info("세이브메퍼: {}",board);
        boardMapper.saveBoard(board);
        //첨부파일 저장
//        if(file != null && file.getSize() > 0) {
//        	// 첨부파일을 서버에 저장
//        	AttachedFile saveFile = fileService.saveFile(file);
//        	//첨부파일 내용을 데이터베이스에 저장
//        	saveFile.setBoard_id(board.getBoard_id());
//        	boardMapper.saveFile(saveFile);        	
//        }
        
//list
     
	}

//	public AttachedFile findFileByAttachedFileId(Long id) {
//		AttachedFile attachedFile = boardMapper.findFileByAttachedFileId(id);
//		return attachedFile;
//	}

	public int getTotal(BoardCategory board_category, String searchText) {
		return boardMapper.getTotal(board_category, searchText);
	}

	public List<Board> findAllBoards(BoardCategory board_category, String searchText, int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		log.info("rowBounds: {}",rowBounds);
		log.info("카테고리: {}",board_category);		
		List<Board> findAllBoards = boardMapper.findAllBoards(board_category, searchText ,rowBounds);
		log.info("서치: {}",searchText);			
		log.info("findAllBoards: {}",findAllBoards);		
		return boardMapper.findAllBoards(board_category, searchText, rowBounds);
	}

	public Board findBoard(Long board_id) {		
		return boardMapper.findBoard(board_id);
	}

	public Board readBoard(Long board_id) {
		Board board = findBoard(board_id);
	    // 조회수 1 증가
//        board.addHit();
        // 조회수를 증가하여 데이터베이스에 업데이트 한다.
//        updateBoard(board, false, null);
		return board;
		
	}

	@Transactional
	public void updateBoard(Board updateBoard /*, boolean isFileRemoved, MultipartFile file*/) {
		Board board = boardMapper.findBoard(updateBoard.getBoard_id());
		
		
		if(board != null) {
			boardMapper.updateBoard(updateBoard);
			// 첨부파일 정보를 가져온다.
//			AttachedFile attachedFile = boardMapper.findFileByBoardId(updateBoard.getBoard_id());
			
//			if(attachedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
//				// 파일 삭제를 요청했거나 새로운 파일이 업로드 됐다면 기존 파일을 삭제한다.	
//				removeAttachedFile(attachedFile.getAttachedFile_id());
//			}
//		}
		
		// 새로 저장할 파일이 있으면 저장한다.
//		if(file != null && file.getSize() > 0) {
			// 첨부파일을 서버에 저장한다.
//			AttachedFile savedFile = fileService.saveFile(file);
			// 데이터베이스 저장될 board_id를 세팅
//			savedFile.setBoard_id(updateBoard.getBoard_id());
			// 첨부파일 내용을 데이터베이스에 저장
//			boardMapper.saveFile(savedFile);
		}
	}
	
//	@Transactional
//	public void removeAttachedFile(Long attachedFile_id) {
//		AttachedFile attachedFile = boardMapper.findFileByAttachedFileId(attachedFile_id);
//		
//		if(attachedFile != null) {
//			//서버에서 삭제
//			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
//			fileService.deleteFile(fullPath);
//			//데이터베이스에서 삭제
//			boardMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
//		}
//	}

//	public AttachedFile findFileByBoardId(Long board_id) {
//		
//		return boardMapper.findFileByBoardId(board_id);
//	}

	
//	@Transactional
//	public void removeAttachedFile(Long attachedFile_id) {
//		AttachedFile attachedFile = boardMapper.findFileByAttachedFileId(attachedFile_id);
//		
//		if(attachedFile != null) {
//			//서버에서 삭제
//			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
//			fileService.deleteFile(fullPath);
//			//데이터베이스에서 삭제
//			boardMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
//		}
//	}


}
