package com.thorben.janssen.spring.data;

import com.thorben.janssen.spring.data.contoller.ChessPlayerController;
import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestApplication {

    private static final Logger log = LoggerFactory.getLogger(TestApplication.class);

    @Autowired
    private ChessPlayerController playerController;

    @Test
    public void testController() {
        log.info("... testController ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");
        
        playerController.persistPlayer(player);
    }

}
