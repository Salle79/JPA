package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thorben.janssen.spring.data.model.ChessGame;
import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestRestApi {

    private static final Logger log = LoggerFactory.getLogger(TestRestApi.class);

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/test-data.sql")
    public void testGetAllPlayers() throws Exception {
        log.info("... testGetAllPlayers ...");

        MvcResult mvcResult = mockMvc.perform(get("/chessPlayers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(5))
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();

        assertThat(responseAsString).isNotBlank();
    }

    @Test
    @Sql("/test-data.sql")
    public void testCreatePlayer() throws Exception {
        log.info("... testCreatePlayer ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");

        MvcResult mvcResult = mockMvc.perform(post("/chessPlayers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        assertThat(location).isNotNull();
        log.info("New ChessPlayer: "+location);

        ChessPlayer chessPlayer = testRestTemplate.getForObject(location.replace("http://localhost", ""), ChessPlayer.class);

        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getFirstName()).isEqualTo(player.getFirstName());
        assertThat(chessPlayer.getLastName()).isEqualTo(player.getLastName());
    }

    @Test
    @Sql("/test-data.sql")
    public void testGetPlayerById() throws Exception {
        log.info("... testGetPlayerById ...");

        ChessPlayer chessPlayer = testRestTemplate.getForObject("/chessPlayers/1", ChessPlayer.class);

        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getFirstName()).isEqualTo("Magnus");

        log.info(chessPlayer.toString());
    }

    @Test
    @Sql("/test-data.sql")
    public void testUpdatePlayer() throws Exception {
        log.info("... testUpdatePlayer ...");

        String newFirstName = "Sven Magnus";

        ChessPlayer chessPlayer = testRestTemplate.getForObject("/chessPlayers/1", ChessPlayer.class);
        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getFirstName()).isEqualTo("Magnus");

        chessPlayer.setFirstName(newFirstName);

        mockMvc.perform(put("/chessPlayers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chessPlayer)))
                .andExpect(status().isNoContent());

        ChessPlayer updatedChessPlayer = testRestTemplate.getForObject("/chessPlayers/1", ChessPlayer.class);
        assertThat(updatedChessPlayer).isNotNull();
        assertThat(updatedChessPlayer.getFirstName()).isEqualTo(newFirstName);
    }

    @Test
    @Sql("/test-data.sql")
    public void testDeleteGame() throws Exception {
        log.info("... testDeleteGame ...");

        ChessGame chessGame = testRestTemplate.getForObject("/games/1", ChessGame.class);
        assertThat(chessGame).isNotNull();
        assertThat(chessGame.getRound()).isEqualTo(4);

        mockMvc.perform(delete("/games/1"))
                .andExpect(status().isNoContent());

        ChessGame deletedChessGame = testRestTemplate.getForObject("/games/1", ChessGame.class);
        assertThat(deletedChessGame).isNull();
    }

    @Test
    @Sql("/test-data.sql")
    public void testGetGamesOfPlayer() throws Exception {
        log.info("... testGetGamesOfPlayer ...");

        MvcResult mvcResult = mockMvc.perform(get("/chessPlayers/1/gamesWhite"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
    }

    @Test
    @Sql("/test-data.sql")
    public void testPlayersSearch() throws Exception {
        log.info("... testPlayersSearch ...");

        MvcResult mvcResult = mockMvc.perform(get("/chessPlayers/search")).andDo(print()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
    }

    @Test
    @Sql("/test-data.sql")
    public void testFindPlayerByLastName() throws Exception {
        log.info("... testFindPlayerByLastName ...");

        ChessPlayer chessPlayer = testRestTemplate.getForObject("/chessPlayers/search/findByLastName?lastName=Carlsen", 
                                                                ChessPlayer.class);

        assertThat(chessPlayer).isNotNull();
        assertThat(chessPlayer.getFirstName()).isEqualTo("Magnus");
    }

    @Test
    @Sql("/test-data.sql")
    public void testPaginationAndSorting() throws Exception {
        log.info("... testPaginationAndSorting ...");

        MvcResult mvcResult = mockMvc.perform(get("/chessPlayers?sort=lastName,asc&size=2&page=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.size").value(2))
                .andExpect(jsonPath("$.page.number").value(1))
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();

        assertThat(responseAsString).isNotBlank();
    }   
}
