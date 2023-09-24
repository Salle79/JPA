package com.thorben.janssen.spring.data.model;

import com.querydsl.core.annotations.QueryProjection;

public class PlayerDto {

    private String firstName;
    private String lastName;

    @QueryProjection
    public PlayerDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
}
