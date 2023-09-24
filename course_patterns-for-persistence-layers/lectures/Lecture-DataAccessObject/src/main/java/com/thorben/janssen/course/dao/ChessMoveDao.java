package com.thorben.janssen.course.dao;

import java.util.List;

import com.thorben.janssen.course.model.ChessMove;

public interface ChessMoveDao {
    
    List<ChessMove> getChessMovesForGame(Long gameId);

    void persistChessMove(ChessMove move);

    void deleteChessMove(ChessMove move);
}
