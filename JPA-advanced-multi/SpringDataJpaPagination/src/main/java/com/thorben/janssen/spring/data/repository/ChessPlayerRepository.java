package com.thorben.janssen.spring.data.repository;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    Page<ChessPlayer> findByLastName(String lastName, Pageable page);

    @Query("SELECT p FROM ChessPlayer p WHERE upper(p.lastName) LIKE upper(:lastName)")
    Page<ChessPlayer> findByNamePage(String lastName, Pageable page);
    
    @Query(value = "SELECT * FROM Chess_Player p WHERE upper(p.last_name) LIKE upper(:lastName)",
            countQuery = "SELECT count(p.id) FROM Chess_Player p WHERE upper(p.last_name) LIKE upper(:lastName)",
            nativeQuery = true)
    Page<ChessPlayer> findByNameNative(String lastName, Pageable page);

    @Query("SELECT p FROM ChessPlayer p WHERE upper(p.lastName) LIKE upper(:lastName)")
    Slice<ChessPlayer> findByNameSlice(String lastName, Pageable page);

    @Query("SELECT p FROM ChessPlayer p WHERE upper(p.lastName) LIKE upper(:lastName)")
    List<ChessPlayer> findByNameList(String lastName, Pageable page);
}