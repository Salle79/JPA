package com.thorben.janssen.course.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thorben.janssen.course.util.HibernateSessionHandling;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
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


    public void persist() {
        HibernateSessionHandling.getSession().persist(this);
        this.getMoves().forEach(m -> m.persist());
    }

    public static List<ChessGame> getGamesByPlayers(String playerWhite, String playerBlack) {
        return HibernateSessionHandling.getSession().createQuery("""
                                                                    SELECT g
                                                                    FROM ChessGame g
                                                                    WHERE g.playerWhite = :playerWhite
                                                                        AND g.playerBlack = :playerBlack""",
                                                    ChessGame.class)
                                                    .setParameter("playerWhite", playerWhite)
                                                    .setParameter("playerBlack", playerBlack)
                                                    .getResultList();
    }


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

    public Set<ChessMove> getMoves() {
        return moves;
    }

    public void setMoves(Set<ChessMove> moves) {
        this.moves = moves;
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
}