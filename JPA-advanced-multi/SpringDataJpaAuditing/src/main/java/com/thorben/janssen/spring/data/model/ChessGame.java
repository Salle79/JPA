package com.thorben.janssen.spring.data.model;

import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ChessGame implements Auditable<String, Long, LocalDateTime> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @SequenceGenerator(name = "game_seq", sequenceName = "game_sequence", initialValue = 100)
    private Long id;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;
    

    private LocalDate date;

    private int round;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessTournament chessTournament;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessPlayer playerWhite;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessPlayer playerBlack;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
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

    @Override
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}