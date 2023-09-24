package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long>, 
                                               RevisionRepository<ChessPlayer, Long, Integer> {

    ChessPlayer findByLastName(String lastName);

}