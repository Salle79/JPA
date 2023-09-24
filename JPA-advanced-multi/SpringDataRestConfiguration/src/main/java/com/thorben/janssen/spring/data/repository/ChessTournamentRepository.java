package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessTournament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tournaments", path = "tournaments")
public interface ChessTournamentRepository extends JpaRepository<ChessTournament, Long> {
}