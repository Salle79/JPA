package com.thorben.janssen.course.dao;

import java.util.List;

import com.thorben.janssen.course.model.ChessGame;

public interface ChessGameDao {

	ChessGame getChessGameById(Long id);

	List<ChessGame> getChessGamesByPlayers(String playerWhite, String playerBlack);

    void persistChessGame(ChessGame game);

    void deleteChessGame(ChessGame game);
}
