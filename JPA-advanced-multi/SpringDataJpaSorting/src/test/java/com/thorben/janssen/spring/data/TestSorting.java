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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.TypedSort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestSorting {

    private static final Logger log = LoggerFactory.getLogger(TestSorting.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void sortByName() {
        log.info("... sortByName ...");

        Sort sort = Sort.by("lastName").ascending();
        List<ChessPlayer> players = playerRepo.findAll(sort);
        assertThat(players.size()).isEqualTo(19);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void sortByMethodReference() {
        log.info("... sortByMethodReference ...");

        Sort sort = Sort.sort(ChessPlayer.class).by(ChessPlayer::getLastName).ascending();
        List<ChessPlayer> players = playerRepo.findByName("%a%", sort);
        assertThat(players.size()).isEqualTo(3);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void sortByMultipleAttributes() {
        log.info("... sortByMultipleAttributes ...");

        TypedSort<ChessPlayer> playerSort = Sort.sort(ChessPlayer.class);
        Sort sort = playerSort.by(ChessPlayer::getLastName).ascending()
                                .and(playerSort.by(ChessPlayer::getFirstName).ascending());
        List<ChessPlayer> players = playerRepo.findByName("%a%", sort);
        assertThat(players.size()).isEqualTo(3);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void sortByMultipleAttributesSameDirection() {
        log.info("... sortByMultipleAttributesSameDirection ...");

        Sort sort = Sort.by(Direction.ASC, "firstName", "lastName");
        List<ChessPlayer> players = playerRepo.findByName("%a%", sort);
        assertThat(players.size()).isEqualTo(3);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void unsorted() {
        log.info("... unsorted ...");

        List<ChessPlayer> players = playerRepo.findByName("%a%", Sort.unsorted());
        assertThat(players.size()).isEqualTo(3);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void sortByFunction() {
        log.info("... sortByFunction ...");

        Sort sort = JpaSort.unsafe(Direction.DESC, "length(lastName)", "length(firstName)");
        List<ChessPlayer> players = playerRepo.findByName("%a%", sort);
        assertThat(players.size()).isEqualTo(3);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }
}
