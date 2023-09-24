package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    List<ChessPlayer> findByLastName(String lastName);

    List<ChessPlayer> streamByLastName(String lastName);

    List<ChessPlayer> findByFirstNameAndLastName(String firstName, String lastName);

    List<ChessPlayer> findFirst10ByLastName(String lastName);

    boolean existsByLastName(String lastName);

    Long countByLastName(String lastName);

    List<ChessPlayer> findByLastNameLike(String lastName);

    List<ChessPlayer> findByLastNameContainingIgnoreCase(String lastName);

    List<ChessPlayer> findByLastNameContainingIgnoreCaseAndFirstName(String lastName, String firstName);

    List<ChessPlayer> findByLastNameNotOrderByLastNameAscFirstNameAsc(String lastName);

    List<ChessPlayer> findByTournamentsName(String name);
}