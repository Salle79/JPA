package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestModification {

    private static final Logger log = LoggerFactory.getLogger(TestModification.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    @Sql("/import-data.sql")
    public void updateQuery() {
        log.info("... updateQuery ...");

        int updateCount = playerRepo.updateByName("Dow", LocalDate.of(1970, 1, 1));
        assertThat(updateCount).isEqualTo(45);
        
        List<ChessPlayer> players = playerRepo.findByLastName("DOW");
        for (ChessPlayer player : players) {
            log.info(player.getId() + " - " 
                    + player.getFirstName() + " " + player.getLastName() 
                    + " was born " + player.getBirthDate()); 
        }
    }

    @Test
    @Sql("/import-data.sql")
    public void outdatedPersistenceContext() {
        log.info("... outdatedPersistenceContext ...");

        playerRepo.findById(20L);

        int updateCount = playerRepo.updateByName("Dow", LocalDate.of(1970, 1, 1));
        assertThat(updateCount).isEqualTo(45);

        ChessPlayer player = playerRepo.findById(20L).get();
        log.info(player.getId() + " - " 
                    + player.getFirstName() + " " + player.getLastName() 
                    + " was born " + player.getBirthDate());    
    }

    @Test
    @Sql("/import-data.sql")
    public void updateNativeQuery() {
        log.info("... updateQuery ...");

        int updateCount = playerRepo.updateByNameNative("Dow", LocalDate.of(1970, 1, 1));
        assertThat(updateCount).isEqualTo(45);
        
        List<ChessPlayer> players = playerRepo.findByLastName("DOW");
        for (ChessPlayer player : players) {
            log.info(player.getId() + " - " 
                    + player.getFirstName() + " " + player.getLastName() 
                    + " was born " + player.getBirthDate()); 
        }
    }


    @Test
    @Sql("/import-data.sql")
    public void deleteDerivedQuery() {
        log.info("... deleteDerivedQuery ...");

        int removeCount = playerRepo.deleteByLastName("Dow");
        assertThat(removeCount).isEqualTo(45);
        
        log.info("Check if players still exist");
        List<ChessPlayer> players = playerRepo.findByLastName("Dow");
        for (ChessPlayer player : players) {
            log.info(player.getId() + " - " 
                    + player.getFirstName() + " " + player.getLastName() 
                    + " was born " + player.getBirthDate()); 
        }
    }

    @Test
    @Sql("/import-data.sql")
    public void deleteBulkDerivedQuery() {
        log.info("... deleteBulkDerivedQuery ...");

        int removeCount = playerRepo.deleteInBulkByLastName("Dow");
        assertThat(removeCount).isEqualTo(45);
        
        log.info("Check if players still exist");
        List<ChessPlayer> players = playerRepo.findByLastName("Dow");
        for (ChessPlayer player : players) {
            log.info(player.getId() + " - " 
                    + player.getFirstName() + " " + player.getLastName() 
                    + " was born " + player.getBirthDate()); 
        }
    }
}
