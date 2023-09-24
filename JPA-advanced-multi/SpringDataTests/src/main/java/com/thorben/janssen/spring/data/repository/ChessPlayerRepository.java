package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    @Query("SELECT p FROM ChessPlayer p WHERE p.lastName = :lastName ORDER BY p.lastName ASC")
    List<ChessPlayer> findByLastName(@Param("lastName") String lastName);
}
