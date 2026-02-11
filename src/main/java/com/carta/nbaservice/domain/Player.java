package com.carta.nbaservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "player")
@Data
@NoArgsConstructor
public class Player implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany(mappedBy = "player")
    private List<PlayerPoints> points;
    private String firstName;
    private String lastName;
}