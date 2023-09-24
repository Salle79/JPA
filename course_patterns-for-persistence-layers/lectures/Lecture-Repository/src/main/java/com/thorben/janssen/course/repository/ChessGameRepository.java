package com.thorben.janssen.course.repository;

import java.util.List;

import com.thorben.janssen.course.model.ChessGame;

public interface ChessGameRepository {

	ChessGame getChessGameById(Long id);

	List<ChessGame> getGamesByPlayers(String playerWhite, String playerBlack);

    void persistChessGame(ChessGame game);

    void deleteChessGame(ChessGame game);
}
