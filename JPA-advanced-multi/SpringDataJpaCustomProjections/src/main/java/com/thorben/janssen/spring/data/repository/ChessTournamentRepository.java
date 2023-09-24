package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessTournament;
import com.thorben.janssen.spring.data.model.TournamentIntf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessTournamentRepository extends JpaRepository<ChessTournament, Long>{
    
    TournamentIntf findByName(String name);

    @Query("SELECT t FROM ChessTournament t LEFT JOIN FETCH t.players WHERE t.name = :name")
    TournamentIntf findByNameWithPlayers(String name);
}
