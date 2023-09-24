package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

public interface PlayerFragmentRepository {
    
    List<ChessPlayer> getByTournamentName(String tournament);
}
