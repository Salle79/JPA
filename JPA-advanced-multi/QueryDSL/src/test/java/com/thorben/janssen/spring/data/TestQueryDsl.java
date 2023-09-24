package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thorben.janssen.spring.data.model.ChessGame;
import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.PlayerDto;
import com.thorben.janssen.spring.data.model.QChessGame;
import com.thorben.janssen.spring.data.model.QChessPlayer;
import com.thorben.janssen.spring.data.model.QPlayerDto;

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
    private EntityManager em;

    @Test
    public void testSimpleQuery() {
        log.info("... testSimpleQuery ...");

        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;

        JPAQuery<ChessPlayer> query = new JPAQuery<>(em);
        ChessPlayer chessPlayer = query.select(qChessPlayer)
                                       .from(qChessPlayer)
                                       .where(qChessPlayer.firstName.eq("Magnus"))
                                       .fetchOne();

        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getFirstName()).isEqualTo("Magnus");
    }

    @Test
    public void testQueryWithMultipleCondition() {
        log.info("... testQueryWithMultipleCondition ...");

        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;

        JPAQuery<ChessPlayer> query = new JPAQuery<>(em);
        ChessPlayer chessPlayer = query.from(qChessPlayer)
                .where(qChessPlayer.firstName.eq("Magnus")
                        .and(qChessPlayer.lastName.eq("Carlsen")))
                .fetchOne();

        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getFirstName()).isEqualTo("Magnus");
        assertThat(chessPlayer.getLastName()).isEqualTo("Carlsen");
    }

    @Test
    public void testJoinQuery() {
        log.info("... testJoinQuery ...");

        QChessGame qChessGame = QChessGame.chessGame;
        QChessPlayer whitePlayer = new QChessPlayer("whitePlayer");
        QChessPlayer blackPlayer = new QChessPlayer("blackPlayer");

        JPAQuery<ChessGame> query = new JPAQuery<>(em);
        ChessGame game = query.from(qChessGame)
                .innerJoin(qChessGame.playerWhite, whitePlayer)
                .innerJoin(qChessGame.playerBlack, blackPlayer)
                .where(whitePlayer.lastName.eq("Caruana")
                        .and(blackPlayer.lastName.eq("van Foreest")))
                .fetchOne();

        assertThat(game).isNotNull();
        assertThat(game.getPlayerWhite().getLastName()).isEqualTo("Caruana");
        assertThat(game.getPlayerBlack().getLastName()).isEqualTo("van Foreest");
    }

    @Test
    public void testOrderByQuery() {
        log.info("... testOrderByQuery ...");

        QChessPlayer chessPlayer = QChessPlayer.chessPlayer;
        JPAQuery<ChessPlayer> query = new JPAQuery<>(em);

        List<ChessPlayer> orderedList = query.from(chessPlayer)
                .orderBy(chessPlayer.lastName.asc(), 
                         chessPlayer.firstName.asc())
                .fetch();

        assertThat(orderedList).isNotEmpty();
        assertThat(orderedList.get(0).getLastName()).isEqualTo("Carlsen");
    }

    @Test
    public void testGroupByQuery() {
        log.info("... testGroupByQuery ...");

        QChessPlayer chessPlayer = QChessPlayer.chessPlayer;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Map<String, Long> group = queryFactory.select(chessPlayer.lastName, 
                                                      chessPlayer.lastName.count())
                .from(chessPlayer)
                .groupBy(chessPlayer.lastName)
                .fetch()
                .stream()
                .collect(Collectors.toMap(tuple -> tuple.get(chessPlayer.lastName), 
                                          tuple -> tuple.get(chessPlayer.lastName.count())));

        assertThat(group).isNotEmpty();
        assertThat(group.get("Dow")).isEqualTo(4);
    }

    @Test
    public void testSubQuery() {
        log.info("... testSubQuery ...");

        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;
        QChessPlayer player = new QChessPlayer("player");

        JPAQuery<ChessPlayer> query = new JPAQuery<>(em);
        List<ChessPlayer> mplayers = query.from(qChessPlayer)
                .where(qChessPlayer.lastName.in(
                        JPAExpressions.select(player.lastName)
                                .from(player)
                                .where(player.lastName.eq("Dow"))))
                .fetch();

        assertThat(mplayers).isNotEmpty();
        assertThat(mplayers.size()).isEqualTo(4);
    }

    @Test
    public void testDtoConstructor() {
        log.info("... testDtoConstructor ...");

        QChessPlayer chessPlayer = QChessPlayer.chessPlayer;
        JPAQuery<ChessPlayer> query = new JPAQuery<>(em);

        List<PlayerDto> orderedList = query
                .select(Projections.constructor(PlayerDto.class, 
                                                chessPlayer.firstName, 
                                                chessPlayer.lastName))
                .from(chessPlayer)
                .fetch();

        assertThat(orderedList).isNotEmpty();
        assertThat(orderedList.get(0).getLastName()).isEqualTo("Carlsen");
    }

    @Test
    public void testDtoProjection() {
        log.info("... testDtoProjection ...");

        QChessPlayer chessPlayer = QChessPlayer.chessPlayer;
        JPAQuery<ChessPlayer> query = new JPAQuery<>(em);

        List<PlayerDto> orderedList = query
                .select(new QPlayerDto(chessPlayer.firstName, 
                                       chessPlayer.lastName))
                .from(chessPlayer)
                .fetch();

        assertThat(orderedList).isNotEmpty();
        assertThat(orderedList.get(0).getLastName()).isEqualTo("Carlsen");
    }

    @Test
    public void testUpdateQuery() {
        log.info("... testUpdateQuery ...");
        
        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        long updated = queryFactory.update(qChessPlayer)
                .where(qChessPlayer.firstName.eq("Magnus"))
                .set(qChessPlayer.firstName, "Sven Magnus")
                .execute();

        assertThat(updated).isEqualTo(1);
    }

    @Test
    public void testDeleteQuery() {
        log.info("... testDeleteQuery ...");
        
        QChessGame qChessGame = QChessGame.chessGame;       
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        
        long deleted = queryFactory.delete(qChessGame)
                .where(qChessGame.round.eq(4))
                .execute();

        assertThat(deleted).isEqualTo(1);
    }
}
