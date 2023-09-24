package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestQueries {

    private static final Logger log = LoggerFactory.getLogger(TestQueries.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void findOne() {
        log.info("... findOne ...");

        // Sample player
        ChessPlayer examplePlayer = new ChessPlayer();
        examplePlayer.setFirstName("Magnus");
        examplePlayer.setLastName("Carlsen");

        Example<ChessPlayer> example = Example.of(examplePlayer);
        Optional<ChessPlayer> player = playerRepo.findOne(example);

        // Validate and log result
        assertThat(player.isPresent());
        assertThat(player.get().getFirstName()).isEqualTo("Magnus");
        log.info(player.get().getFirstName() + " " + player.get().getLastName()); 
    }

    @Test
    public void findAllMatchAny() {
        log.info("... findAllMatchAny ...");

        // Sample player
        ChessPlayer examplePlayer = new ChessPlayer();
        examplePlayer.setFirstName("Magnus");
        examplePlayer.setLastName("Some LastName");

        // Configure matcher
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                                               .withIgnorePaths("version");
        
        Example<ChessPlayer> example = Example.of(examplePlayer, matcher);      
        List<ChessPlayer> players = playerRepo.findAll(example);

        // Validate and log result
        assertThat(players.size()).isEqualTo(1);
        ChessPlayer player = players.get(0);
        assertThat(player.getFirstName()).isEqualTo("Magnus");
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findAllGlobalMatcher() {
        log.info("... findAllGlobalMatcher ...");

        // Sample player
        ChessPlayer examplePlayer = new ChessPlayer();
        examplePlayer.setFirstName("Mag");
        examplePlayer.setLastName("Some LastName");

        // Configure matcher
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                                               .withIgnoreCase()
                                               .withStringMatcher(StringMatcher.CONTAINING)
                                               .withIgnorePaths("version");

        Example<ChessPlayer> example = Example.of(examplePlayer, matcher);
        List<ChessPlayer> players = playerRepo.findAll(example);

        // Validate and log result
        assertThat(players.size()).isEqualTo(1);
        ChessPlayer player = players.get(0);
        assertThat(player.getFirstName()).isEqualTo("Magnus");
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findAllFieldSpecificMatcher() {
        log.info("... findAllFieldSpecificMatcher ...");

        // Sample player
        ChessPlayer examplePlayer = new ChessPlayer();
        examplePlayer.setFirstName("Mag");
        examplePlayer.setLastName("Some LastName");

        // Configure matcher
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                                               .withMatcher("firstName", 
                                                            ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                                               .withIgnorePaths("version");
                                               
        Example<ChessPlayer> example = Example.of(examplePlayer, matcher);
        List<ChessPlayer> players = playerRepo.findAll(example);

        // Validate and log result
        assertThat(players.size()).isEqualTo(1);
        ChessPlayer player = players.get(0);
        assertThat(player.getFirstName()).isEqualTo("Magnus");
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findAllFieldSpecificMatcherLambda() {
        log.info("... findAllFieldSpecificMatcherLambda ...");

        // Sample player
        ChessPlayer examplePlayer = new ChessPlayer();
        examplePlayer.setFirstName("Mag");
        examplePlayer.setLastName("Some LastName");

        // Configure matcher
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                                               .withMatcher("firstName", 
                                                            match -> match.contains().ignoreCase())
                                               .withIgnorePaths("version");

        Example<ChessPlayer> example = Example.of(examplePlayer, matcher);
        List<ChessPlayer> players = playerRepo.findAll(example);

        // Validate and log result
        assertThat(players.size()).isEqualTo(1);
        ChessPlayer player = players.get(0);
        assertThat(player.getFirstName()).isEqualTo("Magnus");
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }
}
