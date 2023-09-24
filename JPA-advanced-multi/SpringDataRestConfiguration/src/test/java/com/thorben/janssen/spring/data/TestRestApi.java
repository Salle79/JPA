package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestRestApi {

    private static final Logger log = LoggerFactory.getLogger(TestRestApi.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllPlayers() throws Exception {
        log.info("... testGetAllPlayers ...");

        MvcResult mvcResult = mockMvc.perform(get("/api/chessPlayers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(5))
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();

        assertThat(responseAsString).isNotBlank();
    }

    @Test
    public void testGetGamesOfPlayer() throws Exception {
        log.info("... testGetGamesOfPlayer ...");

        MvcResult mvcResult = mockMvc.perform(get("/api/chessPlayers/1/gamesWhite"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
    }
}
