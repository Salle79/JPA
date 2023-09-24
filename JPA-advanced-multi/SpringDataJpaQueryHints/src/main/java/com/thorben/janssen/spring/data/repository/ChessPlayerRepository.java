package com.thorben.janssen.spring.data.repository;

import javax.persistence.QueryHint;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;


public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    @QueryHints(value = {@QueryHint(name = "org.hibernate.comment", 
                                   value = "My Comment")},
                forCounting = true)
    Page<ChessPlayer> findByLastName(String lastName, Pageable pageable);

}