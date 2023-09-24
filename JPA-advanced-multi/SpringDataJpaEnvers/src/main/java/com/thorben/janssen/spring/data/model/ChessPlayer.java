package com.thorben.janssen.spring.data.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class ChessPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "player_sequence", initialValue = 100)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "playerWhite")
    private Set<ChessGame> gamesWhite;

    @OneToMany(mappedBy = "playerBlack")
    private Set<ChessGame> gamesBlack;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<ChessGame> getGamesWhite() {
        return gamesWhite;
    }

    public void setGamesWhite(Set<ChessGame> gamesWhite) {
        this.gamesWhite = gamesWhite;
    }

    public Set<ChessGame> getGamesBlack() {
        return gamesBlack;
    }

    public void setGamesBlack(Set<ChessGame> gamesBlack) {
        this.gamesBlack = gamesBlack;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "ChessPlayer [birthDate=" + birthDate + ", firstName=" + firstName + ", id=" + id + ", lastName="
                + lastName + ", version=" + version + "]";
    }

}