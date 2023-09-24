package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    

}