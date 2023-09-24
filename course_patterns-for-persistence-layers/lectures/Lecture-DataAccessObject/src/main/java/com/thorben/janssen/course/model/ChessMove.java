package com.thorben.janssen.course.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class ChessMove {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int moveNumber;

    private String move;
    
    @Enumerated(EnumType.STRING)
    private MoveColor color;

    @ManyToOne
    private ChessGame game;

    @Version
    private int version;

    public Long getId() {
        return id;
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

    public int getVersion() {
        return version;
    }
}
