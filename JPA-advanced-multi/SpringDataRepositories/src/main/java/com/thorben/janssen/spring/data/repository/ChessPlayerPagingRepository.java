package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChessPlayerPagingRepository extends PagingAndSortingRepository<ChessPlayer, Long> {
    
}
