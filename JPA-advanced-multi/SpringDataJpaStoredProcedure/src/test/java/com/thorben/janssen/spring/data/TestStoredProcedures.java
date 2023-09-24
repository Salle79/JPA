package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestStoredProcedures {

    private static final Logger log = LoggerFactory.getLogger(TestStoredProcedures.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void implicitProcedureDefinition() {
        log.info("... implicitProcedureDefinition ...");

        Double result = playerRepo.calculate(1D, 2D);
        assertThat(result).isEqualTo(3D);
    }

    @Test
    public void procedureValue() {
        log.info("... procedureValue ...");

        Double result = playerRepo.callCalculate(1D, 2D);
        assertThat(result).isEqualTo(3D);
    }

    @Test
    public void procedureName() {
        log.info("... performCalculation ...");

        Double result = playerRepo.calculate(1D, 2D);
        assertThat(result).isEqualTo(3D);
    }

    @Test
    public void namedStoredProcedure() {
        log.info("... namedStoredProcedure ...");

        Double result = playerRepo.calculate(1D, 2D);
        assertThat(result).isEqualTo(3D);
    }

}
