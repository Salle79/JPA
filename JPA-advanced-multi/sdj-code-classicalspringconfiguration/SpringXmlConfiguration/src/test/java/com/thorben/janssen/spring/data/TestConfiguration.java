package com.thorben.janssen.spring.data;

import com.thorben.janssen.spring.data.repository.ChessPlayerJpaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@Transactional
public class TestConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TestConfiguration.class);

    @Autowired
    private ChessPlayerJpaRepository playerRepo;

    @Test
    public void testConnection() {
        log.info("... testConnection ...");

        // we will do something useful here ...
        log.info("Number of players: "+playerRepo.findAll().size());
    }
}
