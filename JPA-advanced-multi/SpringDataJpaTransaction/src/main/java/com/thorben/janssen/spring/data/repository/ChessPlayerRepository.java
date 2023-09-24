package com.thorben.janssen.spring.data.repository;

import java.util.Optional;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    Optional<ChessPlayer> findByLastName(String lastName);

    ChessPlayer findByFirstName(String firstName);
}