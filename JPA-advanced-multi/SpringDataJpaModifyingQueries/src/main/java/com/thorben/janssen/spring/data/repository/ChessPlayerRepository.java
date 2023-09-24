package com.thorben.janssen.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    @Modifying (flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE ChessPlayer "
            + "SET lastName = UPPER(lastName), "
                   + "firstName = UPPER(firstName), "
                   + "birthDate = :birthDate "
            + "WHERE lastName = :lastName")
    int updateByName(String lastName, LocalDate birthDate);

    @Modifying (flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Chess_Player "
            + "SET last_name = UPPER(last_name), "
                   + "first_name = UPPER(first_name), "
                   + "birth_date = :birthDate "
            + "WHERE last_name = :lastName",
            nativeQuery = true)
    int updateByNameNative(String lastName, LocalDate birthDate);

    List<ChessPlayer> findByLastName(String lastName);

    @Modifying (flushAutomatically = true, clearAutomatically = true)
    int deleteByLastName(String lastName);

    @Modifying (flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM ChessPlayer p WHERE p.lastName = :lastName")
    int deleteInBulkByLastName(String lastName);
}