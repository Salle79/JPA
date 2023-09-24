package com.thorben.janssen.course.model.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.thorben.janssen.course.model.ChessGame;
import com.thorben.janssen.course.model.ChessMove;
import com.thorben.janssen.course.model.MoveColor;

public class ChessMoveListBuilder {
    
    List<ChessMove> moves = new ArrayList<>();

    private ChessGameBuilder gameBuilder;

    protected ChessMoveListBuilder(ChessGameBuilder gameBuilder) {
        this.gameBuilder = gameBuilder;
    }

    public ChessMoveBuilder addMove() {
        return new ChessMoveBuilder(this);
    }

    public ChessMoveListBuilder addNextMove(String move) {
        ChessMoveBuilder moveBuilder = new ChessMoveBuilder(this);
        if (moves.isEmpty()) {
            // this is the 1st move. white begins
            moveBuilder.withMoveNumber(1);
            moveBuilder.withColor(MoveColor.WHITE);
        } else {
            ChessMove previousMove = moves.get(moves.size()-1);
            if (previousMove.getColor() == MoveColor.WHITE) {
                // black's turn
                moveBuilder.withMoveNumber(previousMove.getMoveNumber());
                moveBuilder.withColor(MoveColor.BLACK);
            } else {
                // white's next move
                moveBuilder.withMoveNumber(previousMove.getMoveNumber()+1);
                moveBuilder.withColor(MoveColor.WHITE);
            }
        }
        moveBuilder.withMove(move);
        return moveBuilder.addToList();
    }

    public ChessGameBuilder endMoves() {
        // This method is only required to navigate back to the ChessGameBuilder
        // Objects are build by ChessMoveBuilder and ChessGameBuilder
        return this.gameBuilder;
    }

    protected ChessMoveListBuilder addMoves(List<ChessMove> moves) {
        moves.forEach(move -> addMove(move));
        return this;
    }

    protected ChessMoveListBuilder addMove(ChessMove move) {
        // validate the provided move
        if (this.moves.isEmpty()) {
            // white makes the 1st move
            if (move.getMoveNumber() != 1 || move.getColor() != MoveColor.WHITE) {
                throw new IllegalArgumentException("A game has to start with white's first move.");
            }
        } else {
            if (move.getColor() == MoveColor.WHITE) {
                // the previous move should have a smaller moveNumber and been made by black
                ChessMove prevMove = moves.get(moves.size()-1);
                if (prevMove.getMoveNumber() != move.getMoveNumber()-1) {
                    throw new IllegalArgumentException("The moveNumber has to get incremented by 1 on each of white's moves.");
                }
                if (prevMove.getColor() != MoveColor.BLACK) {
                    throw new IllegalArgumentException("White is not allowed to move twice.");
                }
            } else {
                // the previous move should have the same moveNumber and been made by white
                ChessMove prevMove = moves.get(moves.size()-1);
                if (prevMove.getMoveNumber() != move.getMoveNumber()) {
                    throw new IllegalArgumentException("The moveNumber can't change on black's moves.");
                }
                if (prevMove.getColor() != MoveColor.WHITE) {
                    throw new IllegalArgumentException("Black is not allowed to move twice.");
                }
            }
        }

        this.moves.add(move);
        return this;
    }
    
    protected void assignMovesToGame(ChessGame game) {
        this.moves.forEach(move -> move.setGame(game));
        game.setMoves(new HashSet<ChessMove>(this.moves));
    }
}