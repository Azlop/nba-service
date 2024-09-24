package com.carta.nbaservice.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 5626606978304933003L;

    private Integer gameId;
    private String text;
}