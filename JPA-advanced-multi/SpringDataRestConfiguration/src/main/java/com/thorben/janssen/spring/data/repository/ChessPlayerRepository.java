package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    @Query("SELECT p FROM ChessPlayer p WHERE p.firstName = :firstName")
    ChessPlayer findByFirstName(String firstName);

    ChessPlayer findByLastName(String lastName);
}