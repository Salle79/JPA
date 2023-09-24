package com.thorben.janssen.spring.data.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
public class ChessGame {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @RestResource(rel = "white", path = "white")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChessPlayer playerWhite;
    
    @RestResource(rel = "black", path = "black")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChessPlayer playerBlack;

    private LocalDate date;

    private int round;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessTournament chessTournament;

    @Version
    private int version;

    public Long getId() {
        return id;
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

    public int getVersion() {
        return version;
    }

    public ChessTournament getChessTournament() {
        return chessTournament;
    }

    public void setChessTournament(ChessTournament chessTournament) {
        this.chessTournament = chessTournament;
    }

    public ChessPlayer getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(ChessPlayer playerWhite) {
        this.playerWhite = playerWhite;
    }

    public ChessPlayer getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(ChessPlayer playerBlack) {
        this.playerBlack = playerBlack;
    }
}