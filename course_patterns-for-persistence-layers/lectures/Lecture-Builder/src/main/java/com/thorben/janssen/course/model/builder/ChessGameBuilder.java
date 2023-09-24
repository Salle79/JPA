package com.thorben.janssen.course.model.builder;

import java.time.LocalDate;
import java.util.List;

import com.thorben.janssen.course.model.ChessGame;
import com.thorben.janssen.course.model.ChessMove;

public class ChessGameBuilder {

    private String playerWhite;
    private String playerBlack;
    private int round;
    private LocalDate date;

    private ChessMoveListBuilder chessMoveListBuilder = new ChessMoveListBuilder(this);

    public ChessGameBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ChessGameBuilder withRound(int round) {
        this.round = round;
        return this;
    }

    public ChessGameBuilder withPlayerWhite(String playerWhite) {
        this.playerWhite = playerWhite;
        return this;
    }

    public ChessGameBuilder withPlayerBlack(String playerBlack) {
        this.playerBlack = playerBlack;
        return this;
    }

    public ChessGameBuilder withMoves(List<ChessMove> moves) {
		this.chessMoveListBuilder = new ChessMoveListBuilder(this);
		this.chessMoveListBuilder.addMoves(moves);
		return this;
    }

    public ChessMoveListBuilder withMoves() {
        return this.chessMoveListBuilder;
    }

    public ChessGame buildChessGame() {
        ChessGame game = new ChessGame();
        game.setRound(round);
        game.setDate(this.date);
        game.setPlayerBlack(this.playerBlack);
        game.setPlayerWhite(this.playerWhite);

        this.chessMoveListBuilder.assignMovesToGame(game);

        return game;
    }
}