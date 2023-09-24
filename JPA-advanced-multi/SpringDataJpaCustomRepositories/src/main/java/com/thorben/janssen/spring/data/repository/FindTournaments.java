package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessTournament;

public interface FindTournaments {

    ChessTournament findByName(String name);

    List<ChessTournament> findByPlayerId(Long playerId);
}
