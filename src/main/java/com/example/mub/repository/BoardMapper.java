package com.example.mub.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.mub.model.board.Board;




@Mapper
public interface BoardMapper {
	void saveBoard(Board board);
	List<Board> findAllBoards(String searchText ,RowBounds rowBounds);
    Board findBoard(Long board_id);
    void addHit(Long board_id);
    void updateBoard(Board updateBoard);
    void removeBoard(Long board_id);
    int getTotal(String searchText);
}
