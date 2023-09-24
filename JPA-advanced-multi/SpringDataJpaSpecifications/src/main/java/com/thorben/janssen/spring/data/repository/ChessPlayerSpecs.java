package com.thorben.janssen.spring.data.repository;

import javax.persistence.criteria.SetJoin;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.ChessPlayer_;
import com.thorben.janssen.spring.data.model.ChessTournament;
import com.thorben.janssen.spring.data.model.ChessTournament_;

import org.springframework.data.jpa.domain.Specification;

public class ChessPlayerSpecs  {

    public static Specification<ChessPlayer> playedInTournament(String tournamentName) {
        return (root, query, builder) -> {
            SetJoin<ChessPlayer, ChessTournament> tournament = root.join(ChessPlayer_.tournaments);
            return builder.equal(tournament.get(ChessTournament_.name), tournamentName);
        };
    }

    public static Specification<ChessPlayer> hasFirstName(String firstName) {
        return (root, query, builder) -> {
            return builder.equal(root.get(ChessPlayer_.firstName), firstName);
        };
    }
}