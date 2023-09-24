package com.thorben.janssen.course.model.fluent.hibernate.fluent;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity(name = "ChessGame_HibernateFluent")
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

    public ChessGame playedOnDate(LocalDate date) {
        this.date = date;
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

    public ChessGame playedInRound(int round) {
        this.round = round;
        return this;
    }

    public ChessGame movesPlayed(Set<ChessMove> moves) {
        this.moves = moves;
        return this;
    }

    public String getPlayerBlack() {
        return playerBlack;
    }

    public String getPlayerWhite() {
        return playerWhite;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }


    public int getRound() {
        return round;
    }


    public int getVersion() {
        return version;
    }

    public Set<ChessMove> getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "ChessGame [id=" + id + ", date=" + date + ", round=" + round + ", playerWhite=" + playerWhite
                + ", playerBlack=" + playerBlack + "]";
    }

    
}