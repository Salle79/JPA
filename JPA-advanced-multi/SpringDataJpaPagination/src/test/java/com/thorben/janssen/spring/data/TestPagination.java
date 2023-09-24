package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestPagination {

    private static final Logger log = LoggerFactory.getLogger(TestPagination.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void findAll() {
        log.info("... findAll ...");

        PageRequest page = PageRequest.of(1, 5, Sort.by("lastName").ascending());
        Page<ChessPlayer> players = playerRepo.findAll(page);
        assertThat(players.getNumber()).isEqualTo(1);
        assertThat(players.getSize()).isEqualTo(5);
        assertThat(players.getNumberOfElements()).isEqualTo(5);
        assertThat(players.getTotalPages()).isEqualTo(4);
        assertThat(players.getTotalElements()).isEqualTo(19);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void findByNameDerived() {
        log.info("... findByNameDerived ...");

        PageRequest page = PageRequest.of(1, 5, Sort.by("lastName"));
        Page<ChessPlayer> players = playerRepo.findByLastName("Doe", page);
        assertThat(players.getNumber()).isEqualTo(1);
        assertThat(players.getNumberOfElements()).isEqualTo(5);
        assertThat(players.getTotalPages()).isEqualTo(3);
        assertThat(players.getTotalElements()).isEqualTo(15);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void findByNamePage() {
        log.info("... findByNamePage ...");

        PageRequest page = PageRequest.of(1, 5, Sort.by("lastName"));
        Page<ChessPlayer> players = playerRepo.findByNamePage("Doe", page);
        assertThat(players.getNumber()).isEqualTo(1);
        assertThat(players.getNumberOfElements()).isEqualTo(5);
        assertThat(players.getTotalPages()).isEqualTo(3);
        assertThat(players.getTotalElements()).isEqualTo(15);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void findByNameNative() {
        log.info("... findByNameNative ...");

        PageRequest page = PageRequest.of(1, 5, Sort.by("last_name"));
        Page<ChessPlayer> players = playerRepo.findByNameNative("Doe", page);
        assertThat(players.getNumber()).isEqualTo(1);
        assertThat(players.getNumberOfElements()).isEqualTo(5);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void findByNameSlice() {
        log.info("... findByNameSlice ...");

        PageRequest page = PageRequest.of(1, 5, Sort.by("lastName"));
        Slice<ChessPlayer> players = playerRepo.findByNameSlice("Doe", page);
        assertThat(players.getNumber()).isEqualTo(1);
        assertThat(players.getNumberOfElements()).isEqualTo(5);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void findByNameList() {
        log.info("... findByNameList ...");

        PageRequest page = PageRequest.of(1, 5, Sort.by("lastName"));
        List<ChessPlayer> players = playerRepo.findByNameList("Doe", page);
        assertThat(players.size()).isEqualTo(5);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void unpaged() {
        log.info("... unpaged ...");

        Page<ChessPlayer> players = playerRepo.findByNameNative("Doe", Pageable.unpaged());
        assertThat(players.getNumberOfElements()).isEqualTo(15);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

}
