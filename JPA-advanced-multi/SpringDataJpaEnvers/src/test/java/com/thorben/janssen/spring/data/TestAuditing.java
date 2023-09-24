package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
public class TestAuditing {

    private static final Logger log = LoggerFactory.getLogger(TestAuditing.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Autowired 
    private TransactionTemplate tx;

    @Test
    public void testWriteAuditLog() {
        log.info("... testWriteAuditLog ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");
        tx.execute(___ -> playerRepo.save(player));    
    }

    @Test
    public void testReadAllRevisions() {
        log.info("... testReadAllRevisions ...");

        Long playerId = prepareTestData();
        
        Revisions<Integer, ChessPlayer> revisions = playerRepo.findRevisions(playerId);
        assertThat(revisions).hasSize(2);
        for (Revision<Integer, ChessPlayer> rev : revisions) {
            log.info("Revision number: "+rev.getRevisionNumber().get());
            log.info("Revision instant: "+rev.getRevisionInstant().get());
            log.info(rev.getEntity().toString());
        }
    }

    @Test
    public void testReadLastestChange() {
        log.info("... testReadLastestChange ...");

        Long playerId = prepareTestData();
        
        Optional<Revision<Integer, ChessPlayer>> latestChange = playerRepo.findLastChangeRevision(playerId);
        assertThat(latestChange).isPresent();
        RevisionMetadata<Integer> revMeta = latestChange.get().getMetadata();
        log.info("Revision number: "+revMeta.getRevisionNumber().get());
        log.info("Revision instant: "+revMeta.getRevisionInstant().get());
        log.info("Revision type: "+revMeta.getRevisionType());
        log.info(latestChange.get().getEntity().toString());
    }

    @Test
    public void testReadSpecificRevision() {
        log.info("... testReadLastestChange ...");

        Long playerId = prepareTestData();
        Integer firstRevisionNumber = getFirstRevisionNumber(playerId);
        
        Optional<Revision<Integer, ChessPlayer>> firstRevision = playerRepo.findRevision(playerId, firstRevisionNumber);
        assertThat(firstRevision).isPresent();
        
        log.info("Revision number: "+firstRevision.get().getRevisionNumber().get());
        log.info("Revision instant: "+firstRevision.get().getRevisionInstant().get());
        log.info(firstRevision.get().getEntity().toString());
    } 
    
    
    // Prepare test data

    public Long prepareTestData() {
        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Torben");
        player.setLastName("Janssen");
        ChessPlayer saved = tx.execute(___ -> playerRepo.save(player));
        
        saved.setFirstName("Thorben");
        tx.execute(___ -> playerRepo.save(saved));

        return player.getId();
    }

    public Integer getFirstRevisionNumber(Long playerId) {
        Revisions<Integer, ChessPlayer> revisions = playerRepo.findRevisions(playerId);
        Revision<Integer, ChessPlayer> rev = revisions.iterator().next();
        log.info("" + rev.getMetadata().getRevisionNumber().get());
        return rev.getMetadata().getRevisionNumber().get();

    }
}
