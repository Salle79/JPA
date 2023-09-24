package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.repository.CrudRepository;

public interface ChessPlayerCrudRepository extends CrudRepository<ChessPlayer, Long> {
    
}
