package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.QChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessGameRepository;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestQueryDsl {

    private static final Logger log = LoggerFactory.getLogger(TestQueryDsl.class);

    @Autowired
    private ChessPlayerRepository chessPlayerRepository;

    @Test
    public void testQueryWithMultipleCondition() {
        log.info("... testQueryWithMultipleCondition ...");

        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;

        BooleanExpression predicate = qChessPlayer.firstName.eq("Magnus")
                                        .and(qChessPlayer.lastName.eq("Carlsen"));

        Optional<ChessPlayer> chessPlayer = chessPlayerRepository.findOne(predicate);

        assertThat(chessPlayer).isPresent();
        assertThat(chessPlayer.get().getFirstName()).isEqualTo("Magnus");
    }

    @Test
    public void testOrderByQuery() {
        log.info("... testOrderByQuery ...");
        
        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;
        OrderSpecifier<String> firstNameAsc = qChessPlayer.firstName.asc();
        OrderSpecifier<String> lastNameDesc = qChessPlayer.lastName.asc();

        Iterable<ChessPlayer> orderedIterable = chessPlayerRepository.findAll(lastNameDesc, 
                                                                              firstNameAsc);

        List<ChessPlayer> orderedList = new ArrayList<>();
        orderedIterable.forEach(orderedList::add);
        assertThat(orderedList).isNotEmpty();
        assertThat(orderedList.get(0).getLastName()).isEqualTo("Carlsen");
    }

    @Test
    public void testSubQuery() {
        log.info("... testSubQuery ...");

        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;
        QChessPlayer player = new QChessPlayer("player");

        BooleanExpression subQueryPredicate = qChessPlayer.lastName.in(
                JPAExpressions.select(player.lastName)
                        .from(player)
                        .where(player.lastName.eq("Dow")));

        Iterable<ChessPlayer> chessPlayerIterable = chessPlayerRepository.findAll(subQueryPredicate);
        
        List<ChessPlayer> players = new ArrayList<>();
        chessPlayerIterable.forEach(players::add);
        assertThat(players).isNotEmpty();
        assertThat(players.size()).isEqualTo(2);
    }

    @Test
    public void fragmentRepository() {
        log.info("... fragmentRepository ...");

        List<ChessPlayer> players = chessPlayerRepository.getByTournamentName("Tata Steel Chess Tournament 2021");
        assertThat(players.size()).isEqualTo(4);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }
}
