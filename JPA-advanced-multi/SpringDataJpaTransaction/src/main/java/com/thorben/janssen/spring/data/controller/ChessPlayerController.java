package com.thorben.janssen.spring.data.controller;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;
import com.thorben.janssen.spring.data.repository.MyException;
import com.thorben.janssen.spring.data.repository.MyRuntimeException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "chessPlayer")
public class ChessPlayerController {
    
    private ChessPlayerRepository playerRepo;

    public ChessPlayerController(ChessPlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    public void persistWithoutTransaction(ChessPlayer p) {
        playerRepo.save(p);

        throw new RuntimeException("Let it fail ...");
    }

    @Transactional
    public void persistWithTransaction(ChessPlayer p) {
        playerRepo.save(p);

        throw new RuntimeException("Let it fail ...");
    }

    @Transactional (rollbackFor = MyException.class)
    public void persistWithException(ChessPlayer p) throws MyException {
        playerRepo.save(p);

        throw new MyException();
    }

    @Transactional (noRollbackFor = MyRuntimeException.class)
    public void persistWithRuntimeException(ChessPlayer p) {
        playerRepo.save(p);

        throw new MyRuntimeException();
    }
}
