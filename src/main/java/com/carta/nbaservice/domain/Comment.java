package com.carta.nbaservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

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