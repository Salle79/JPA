package com.thorben.janssen.course.model.fluent.hibernate.setter;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "ChessMove_HibernateSetter")
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

    public ChessMove setId(Long id) {
        this.id = id;
        return this;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public ChessMove setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
        return this;
    }

    public MoveColor getColor() {
        return color;
    }

    public ChessMove setColor(MoveColor color) {
        this.color = color;
        return this;
    }

    public String getMove() {
        return move;
    }

    public ChessMove setMove(String move) {
        this.move = move;
        return this;
    }

    public ChessGame getGame() {
        return game;
    }

    public ChessMove setGame(ChessGame game) {
        this.game = game;
        return this;
    }

    
}
