package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.PlayerFullNameIntf;
import com.thorben.janssen.spring.data.model.PlayerName;
import com.thorben.janssen.spring.data.model.PlayerNameIntf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    List<ChessPlayer> findByLastName(String lastName);

    @Query("SELECT p FROM ChessPlayer p WHERE p.lastName = :lastName")
    List<ChessPlayer> findByName(String lastName);
    
    @Query(value = "SELECT p.firstName, p.lastName FROM ChessPlayer p")
    List<Object[]> getPlayerNames();

    @Query(value = "SELECT new com.thorben.janssen.spring.data.model.PlayerName(p.firstName, p.lastName) FROM ChessPlayer p")
    List<PlayerName> getPlayerNamesConstrutorExpression();

    List<PlayerName> findPlayerNamesBy();

    List<PlayerNameIntf> findPlayerNamesIntfBy();

    List<PlayerFullNameIntf> findPlayerFullNamesBy();

    <T> T findPlayerByLastName(String lastName, Class<T> type);
}