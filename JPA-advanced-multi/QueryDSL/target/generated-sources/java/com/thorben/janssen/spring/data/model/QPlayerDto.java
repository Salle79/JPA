package com.thorben.janssen.spring.data.model;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.thorben.janssen.spring.data.model.QPlayerDto is a Querydsl Projection type for PlayerDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QPlayerDto extends ConstructorExpression<PlayerDto> {

    private static final long serialVersionUID = 2094156353L;

    public QPlayerDto(com.querydsl.core.types.Expression<String> firstName, com.querydsl.core.types.Expression<String> lastName) {
        super(PlayerDto.class, new Class<?>[]{String.class, String.class}, firstName, lastName);
    }

}

