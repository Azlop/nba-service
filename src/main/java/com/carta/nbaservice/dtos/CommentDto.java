package com.carta.nbaservice.dtos;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class CommentDto {

    @Id
    @GeneratedValue
    private Integer commentId;
    @Column
    private String text;
    @Column
    private Timestamp timestamp;

    public CommentDto(String text, Timestamp timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public CommentDto() {}

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

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
