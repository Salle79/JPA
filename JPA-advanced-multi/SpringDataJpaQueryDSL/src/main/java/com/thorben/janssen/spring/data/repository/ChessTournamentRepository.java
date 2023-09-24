package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessTournament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChessTournamentRepository extends JpaRepository<ChessTournament, Long>, QuerydslPredicateExecutor<ChessTournament> {

}