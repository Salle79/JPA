package com.thorben.janssen.course.model.fluent.jpa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity(name = "ChessGame_Jpa")
public class ChessGame {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate date;

    private int round;

    private String playerWhite;
    
    private String playerBlack;

    @OneToMany(mappedBy = "game")
    private Set<ChessMove> moves = new HashSet<>();

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(String playerWhite) {
        this.playerWhite = playerWhite;
    }

    public String getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(String playerBlack) {
        this.playerBlack = playerBlack;
    }

    public Set<ChessMove> getMoves() {
        return moves;
    }

    public void setMoves(Set<ChessMove> moves) {
        this.moves = moves;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ChessGame playedOnDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ChessGame playedInRound(int round) {
        this.round = round;
        return this;
    }

    public ChessGame movesPlayed(Set<ChessMove> moves) {
        this.moves = moves;
        return this;
    }

    public ChessGame whitePiecesPlayedBy(String playerWhite) {
        this.playerWhite = playerWhite;
        return this;
    }

    public ChessGame blackPiecesPlayedBy(String playerBlack) {
        this.playerBlack = playerBlack;
        return this;
    }

    @Override
    public String toString() {
        return "ChessGame [id=" + id + ", date=" + date + ", round=" + round + ", playerWhite=" + playerWhite
                + ", playerBlack=" + playerBlack + "]";
    }

    
}