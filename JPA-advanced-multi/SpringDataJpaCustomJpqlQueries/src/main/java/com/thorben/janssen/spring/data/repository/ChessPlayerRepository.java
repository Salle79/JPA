package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    // same statement as derived for findByLastNameContainingIgnoreCaseAndFirstName method
    @Query("SELECT p FROM ChessPlayer p WHERE upper(p.lastName) LIKE upper(:lastName) AND p.firstName = :firstName")
    List<ChessPlayer> findByName(String lastName, String firstName);

    @Query("FROM ChessPlayer p WHERE upper(p.lastName) LIKE upper(:lName) AND p.firstName = :firstName")
    List<ChessPlayer> findByNameWithoutSelect(@Param("lName") String lastName, String firstName);

    @Query("SELECT p FROM ChessPlayer p JOIN p.tournaments t "
            + "WHERE p.lastName LIKE :lastName AND p.firstName LIKE :firstName AND t.name = :tournamentName "
            + "ORDER BY p.lastName ASC, p.firstName ASC")
    List<ChessPlayer> findByNameAndTournamentOrderedByPlayerName(String lastName, String firstName, String tournamentName);

    @Query("SELECT p FROM ChessPlayer p "
            + "WHERE p.lastName LIKE %:lastName% AND p.firstName LIKE %:firstName% "
            + "ORDER BY p.lastName ASC, p.firstName ASC")
    List<ChessPlayer> findByNameAndTournamentOrderedAdvancedLike(String lastName, String firstName);
}