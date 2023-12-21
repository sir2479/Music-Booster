package com.example.mub.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.example.mub.model.board.Board;
import com.example.mub.model.board.BoardCategory;




@Mapper
public interface BoardMapper {
	void saveBoard(Board board);
	List<Board> findAllBoards(@Param("board_category")BoardCategory board_category, @Param("searchText")String searchText ,RowBounds rowBounds);
    Board findBoard(Long board_id);
    void addHit(Long board_id);
    void updateBoard(Board updateBoard);
    void removeBoard(Long board_id);
    int getTotal(@Param("board_category") BoardCategory board_category, @Param("searchText")String searchText);
}
