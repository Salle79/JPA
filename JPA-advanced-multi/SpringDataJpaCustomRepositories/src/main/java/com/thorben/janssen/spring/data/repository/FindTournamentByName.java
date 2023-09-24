package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessTournament;

public interface FindTournamentByName {

    ChessTournament findByName(String name);

}
