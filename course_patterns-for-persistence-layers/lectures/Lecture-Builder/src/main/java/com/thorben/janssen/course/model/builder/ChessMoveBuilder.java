package com.thorben.janssen.course.model.builder;

import com.thorben.janssen.course.model.ChessMove;
import com.thorben.janssen.course.model.MoveColor;

public class ChessMoveBuilder {

    private ChessMoveListBuilder chessMoveListBuilder;

    private int moveNumber;
    private MoveColor color;
    private String move;

    protected ChessMoveBuilder(ChessMoveListBuilder chessMoveListBuilder) {
        this.chessMoveListBuilder = chessMoveListBuilder;
    }

    public ChessMoveBuilder withMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
        return this;
    }

    public ChessMoveBuilder withColor(MoveColor color) {
        this.color = color;
        return this;
    }

    public ChessMoveBuilder withMove(String move) {
        this.move = move;
        return this;
    }

    private ChessMove build() {
        ChessMove chessMove = new ChessMove();
        chessMove.setColor(this.color);
        chessMove.setMove(this.move);
        chessMove.setMoveNumber(this.moveNumber);
        return chessMove;
    }

    public ChessMoveListBuilder addToList() {
        ChessMove chessMove = build();
        this.chessMoveListBuilder.addMove(chessMove);
        return chessMoveListBuilder;
    }
}