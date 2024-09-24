package com.carta.nbaservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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