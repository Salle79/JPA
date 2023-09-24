package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChessGameRepository extends JpaRepository<ChessGame, Long>, QuerydslPredicateExecutor<ChessGame> {

}
