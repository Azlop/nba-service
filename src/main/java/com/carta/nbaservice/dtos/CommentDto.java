package com.carta.nbaservice.dtos;

import java.io.Serializable;
import java.util.Objects;

public class CommentDto implements Serializable {
    private static final long serialVersionUID = 5626606978304933003L;

    private Integer gameId;
    private String text;

    public CommentDto() {}

    public CommentDto(Integer gameId, String text) {
        this.gameId = gameId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, text);
    }
}
