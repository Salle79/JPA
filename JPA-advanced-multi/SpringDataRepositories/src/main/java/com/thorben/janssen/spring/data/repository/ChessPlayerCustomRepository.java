package com.thorben.janssen.spring.data.repository;

import java.util.List;
import java.util.Optional;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.repository.Repository;

public interface ChessPlayerCustomRepository extends Repository<ChessPlayer, Long> {

    ChessPlayer save(ChessPlayer player);

    Optional<ChessPlayer> findById(Long id);

    List<ChessPlayer> findAll();
}
