package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import com.thorben.janssen.spring.data.model.ChessGame;
import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.ChessTournament;
import com.thorben.janssen.spring.data.repository.ChessGameRepository;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;
import com.thorben.janssen.spring.data.repository.ChessTournamentRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestAuditing {

    private static final Logger log = LoggerFactory.getLogger(TestAuditing.class);

    @Autowired
    private ChessPlayerRepository playerRepo;
    @Autowired
    private ChessTournamentRepository chessTournamentRepository;
    @Autowired
    private ChessGameRepository chessGameRepository;

    @Test
    public void testAnnotationBasedAuditing() {
        log.info("... testAnnotationBasedAuditing ...");

        ChessTournament chessTournament = new ChessTournament();
        chessTournament.setName("tournament");
        chessTournament.setStartDate(LocalDate.of(2021, 5, 1));
        chessTournament.setEndDate(LocalDate.of(2021, 6, 1));

        chessTournamentRepository.save(chessTournament);

        chessTournament.setName("My Tournament");

        ChessTournament tournament = chessTournamentRepository.findByName(chessTournament.getName());

        assertThat(tournament).isNotNull();
        assertThat(tournament.getCreatedBy()).isEqualTo("thorben.janssen");
        assertThat(tournament.getCreationDate()).isNotNull();
        assertThat(tournament.getLastModifiedBy()).isEqualTo("thorben.janssen");
        assertThat(tournament.getLastModifiedDate()).isNotNull();
        assertThat(tournament.getCreationDate()).isBefore(tournament.getLastModifiedDate());
    }

    @Test
    public void testSuperClassAuditing() {
        log.info("... testSuperClassAuditing ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Torben");
        player.setLastName("Janssen");

        playerRepo.save(player);

        player.setFirstName("Thorben");

        ChessPlayer chessPlayer = playerRepo.findByLastName("Janssen");

        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getCreatedBy()).isNotBlank();
        assertThat(chessPlayer.getCreatedBy()).isEqualTo("thorben.janssen");
        assertThat(chessPlayer.getCreationDate()).isNotNull();
        assertThat(chessPlayer.getLastModifiedBy()).isEqualTo("thorben.janssen");
        assertThat(chessPlayer.getLastModifiedDate()).isNotNull();
        assertThat(chessPlayer.getCreationDate()).isBefore(chessPlayer.getLastModifiedDate());
    }

    @Test
    public void testInterfaceBasedAuditing() {
        log.info("... testInterfaceBasedAuditing ...");

        ChessGame game = new ChessGame();
        game.setRound(1);
        game.setDate(LocalDate.of(2021, 5, 1));

        chessGameRepository.save(game);

        game.setDate(LocalDate.of(2021, 5, 2));

        ChessGame chessGame = chessGameRepository.findByRound(1);

        assertThat(chessGame).isNotNull();
        assertThat(chessGame.getCreatedBy()).isPresent();
        assertThat(chessGame.getCreatedBy()).hasValue("thorben.janssen");
        assertThat(chessGame.getCreatedDate()).isPresent();
        assertThat(chessGame.getLastModifiedBy()).isPresent();
        assertThat(chessGame.getLastModifiedBy()).hasValue("thorben.janssen");
        assertThat(chessGame.getLastModifiedDate()).isPresent();
        assertThat(chessGame.getCreatedDate().get()).isBefore(chessGame.getLastModifiedDate().get());

    }

    
}
