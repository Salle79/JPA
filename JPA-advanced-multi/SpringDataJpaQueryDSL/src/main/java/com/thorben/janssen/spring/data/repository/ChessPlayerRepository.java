package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long>, 
                                               QuerydslPredicateExecutor<ChessPlayer>,
                                               PlayerFragmentRepository {

    @Query("SELECT p FROM ChessPlayer p WHERE p.firstName = :firstName")
    List<ChessPlayer> findByFirstName(String firstName);

    List<ChessPlayer> findByLastName(String lastName);
}