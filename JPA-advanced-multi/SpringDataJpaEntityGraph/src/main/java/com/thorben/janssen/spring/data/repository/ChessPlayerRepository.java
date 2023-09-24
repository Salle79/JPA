package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    @EntityGraph(value = "graph.playerTournament", type = EntityGraphType.FETCH)
    List<ChessPlayer> findByLastName(String lastName);

    @EntityGraph(attributePaths = "tournaments", type = EntityGraphType.FETCH)
    List<ChessPlayer> findWithSimpleGraphByLastName(String lastName);

    @EntityGraph(attributePaths = "tournaments.games", type = EntityGraphType.FETCH)
    List<ChessPlayer> findWithComplexGraphByLastName(String lastName);
}