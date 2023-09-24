package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "games", collectionResourceRel = "games", itemResourceRel = "game")
public interface ChessGameRepository extends JpaRepository<ChessGame, Long> {
}
