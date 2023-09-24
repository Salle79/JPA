package com.thorben.janssen.spring.data.contoller;

import javax.transaction.Transactional;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "player")
@Transactional
public class ChessPlayerController {
    
    private ChessPlayerRepository playerRepo;

    public ChessPlayerController(ChessPlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    @PostMapping
    public void persistPlayer(ChessPlayer player) {
        // validate input

        // persist ChessPlayer
        playerRepo.save(player);

        // perform other business logic
    }
}
