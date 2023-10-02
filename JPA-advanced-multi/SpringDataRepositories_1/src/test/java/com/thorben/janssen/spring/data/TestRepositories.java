package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerCrudRepository;
import com.thorben.janssen.spring.data.repository.ChessPlayerCustomRepository;
import com.thorben.janssen.spring.data.repository.ChessPlayerJpaRepository;
import com.thorben.janssen.spring.data.repository.ChessPlayerPagingRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestRepositories {

    private static final Logger log = LoggerFactory.getLogger(TestRepositories.class);

    @Autowired
    private ChessPlayerJpaRepository playerJpaRepo;

    @Autowired
    private ChessPlayerPagingRepository playerPagingRepo;

    @Autowired
    private ChessPlayerCrudRepository playerCrudRepo;

    @Autowired
    private ChessPlayerCustomRepository playerCustomRepo;

    @Test
    @Sql("/test_data.sql")
    public void testCrudRepo() {
        log.info("... testCrudRepo ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");

        playerCrudRepo.save(player);

        assertThat(playerCrudRepo.findById(player.getId())).hasValue(player);

        playerCrudRepo.findAll();
    }

    @Test
    @Sql("/test_data.sql")
    public void testPagingRepo() {
        log.info("... testPagingRepo ...");

        Pageable pageable = PageRequest.of(1, 10);
        Page<ChessPlayer> players = playerPagingRepo.findAll(pageable);

        assertThat(players.getTotalElements()).isEqualTo(4);
        assertThat(players.getTotalPages()).isEqualTo(1);
    }

    @Test
    @Sql("/test_data.sql")
    public void testJpaRepo() {
        log.info("... testJpaRepo ...");

        List<ChessPlayer> players = playerJpaRepo.findAll();
        playerJpaRepo.deleteInBatch(players);

        assertThat(playerJpaRepo.findAll().size()).isEqualTo(0);
    }

    @Test
    @Sql("/test_data.sql")
    public void testCustomRepo() {
        log.info("... testCustomRepo ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");

        playerCustomRepo.save(player);

        assertThat(playerCustomRepo.findById(player.getId())).hasValue(player);

        playerCustomRepo.findAll();
    }

    @Test
    @Sql("/test_data.sql")
    public void debugCrudRepo() {
        log.info("... debugCrudRepo ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Jansen");

        playerCrudRepo.save(player);

        player.setLastName("Janssen");
        // playerCrudRepo.save(player);

        assertThat(playerCrudRepo.findById(player.getId())).hasValue(player);

        playerCrudRepo.findAll();
    }
}
