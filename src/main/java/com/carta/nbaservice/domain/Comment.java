package com.carta.nbaservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Integer commentId;
    @Column
    private Integer gameId;
    @Column
    private String text;
    @Column
    private Timestamp timestamp;
}