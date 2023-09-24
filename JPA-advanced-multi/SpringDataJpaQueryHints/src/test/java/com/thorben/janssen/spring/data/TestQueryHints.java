package com.thorben.janssen.spring.data;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestQueryHints {

    private static final Logger log = LoggerFactory.getLogger(TestQueryHints.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void useQueryHint() {
        log.info("... useQueryHint ...");

        Sort sort = Sort.sort(ChessPlayer.class).by(ChessPlayer::getLastName).ascending();
        Page<ChessPlayer> players = playerRepo.findByLastName("Doe", PageRequest.of(1, 5, sort));
        
        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName()); 
        }
    }

}
