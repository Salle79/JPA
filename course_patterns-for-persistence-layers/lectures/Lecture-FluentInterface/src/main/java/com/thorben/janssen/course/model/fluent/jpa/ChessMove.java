package com.thorben.janssen.course.model.fluent.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "ChessMove_Jpa")
public class ChessMove {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int moveNumber;

    @Enumerated(EnumType.STRING)
    private MoveColor color;

    private String move;

    @ManyToOne
    private ChessGame game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public MoveColor getColor() {
        return color;
    }

    public void setColor(MoveColor color) {
        this.color = color;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public ChessMove playedOnMove(int moveNumber) {
        this.moveNumber = moveNumber;
        return this;
    }

    public ChessMove playedByColor(MoveColor color) {
        this.color = color;
        return this;
    }

    public ChessMove playedMove(String move) {
        this.move = move;
        return this;
    }

    public ChessMove playedInGame(ChessGame game) {
        this.game = game;
        return this;
    }
}
