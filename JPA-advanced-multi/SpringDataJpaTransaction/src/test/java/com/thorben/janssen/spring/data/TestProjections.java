package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import com.thorben.janssen.spring.data.controller.ChessPlayerController;
import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;
import com.thorben.janssen.spring.data.repository.MyException;
import com.thorben.janssen.spring.data.repository.MyRuntimeException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestProjections {

    private static final Logger log = LoggerFactory.getLogger(TestProjections.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Autowired
    private ChessPlayerController playerController;


    @Test
    public void withoutTransaction() {
        log.info("... withoutTransaction ...");

        String randomLastName = UUID.randomUUID().toString();

        ChessPlayer p = new ChessPlayer();
        p.setLastName(randomLastName);

        try {
            playerController.persistWithoutTransaction(p);
        } catch(RuntimeException e) {
            log.info("Caught expected exception: "+e);
        }

        Optional<ChessPlayer> player = playerRepo.findByLastName(randomLastName);
        assertThat(player.isPresent()).isTrue();
        log.info("Found player: "+player.isPresent());
    }

    @Test
    public void withTransaction() {
        log.info("... withTransaction ...");

        boolean wasPersisted = true;
        String randomLastName = UUID.randomUUID().toString();

        ChessPlayer p = new ChessPlayer();
        p.setLastName(randomLastName);

        try {
            playerController.persistWithTransaction(p);
        } catch(RuntimeException e) {
            log.info("Caught expected exception: "+e);
            log.info("Transaction should be rolled back");
            wasPersisted = false;
        }

        Optional<ChessPlayer> player = playerRepo.findByLastName(randomLastName);
        assertThat(player.isPresent()).isEqualTo(wasPersisted);
        log.info("Found player: "+player.isPresent());
    }

    @Test
    public void persistWithException() {
        log.info("... persistWithException ...");

        boolean wasPersisted = true;
        String randomLastName = UUID.randomUUID().toString();

        ChessPlayer p = new ChessPlayer();
        p.setLastName(randomLastName);

        try {
            playerController.persistWithException(p);
        } catch(MyException e) {
            log.info("Caught expected exception: "+e);
            log.info("Transaction should be rolled back");
            wasPersisted = false;
        }

        Optional<ChessPlayer> player = playerRepo.findByLastName(randomLastName);
        log.info("Found player: "+player.isPresent());
        assertThat(player.isPresent()).isEqualTo(wasPersisted);
    }

    @Test
    public void persistWithRuntimeException() {
        log.info("... persistWithRuntimeException ...");

        boolean wasPersisted = true;
        String randomLastName = UUID.randomUUID().toString();

        ChessPlayer p = new ChessPlayer();
        p.setLastName(randomLastName);

        try {
            playerController.persistWithRuntimeException(p);
        } catch(MyRuntimeException e) {
            log.info("Caught expected exception: "+e);
            log.info("Transaction should be rolled back");
        }

        Optional<ChessPlayer> player = playerRepo.findByLastName(randomLastName);
        log.info("Found player: "+player.isPresent());
        assertThat(player.isPresent()).isEqualTo(wasPersisted);
    }
}
