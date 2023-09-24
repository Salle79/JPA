package com.thorben.janssen.spring.data.repository;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    @Procedure
    Double calculate(Double x, Double y);

    @Procedure("calculate")
    Double callCalculate(@Param("x") Double summand1, @Param("y") Double summand2);

    @Procedure(procedureName = "calculate")
    Double performCalculation(Double x, Double y);

    @Procedure(name = "named.calculate")
    Double calculateNamed(Double x, Double y);

}