package com.carta.nbaservice.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Integer commentId;
    @Column
    private long gameId;
    @Column
    private String text;
    @Column
    private Timestamp timestamp;

    public Comment(long gameId, String text) {
        this.gameId = gameId;
        this.text = text;
        setTimestamp();
    }

    protected Comment() {}

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    private void setTimestamp() {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
