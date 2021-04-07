package com.carta.nbaservice.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "player")
    private List<PlayerPoints> points;

    @Column
    private String firstName;
    @Column
    private String lastName;

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Player() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer playerId) {
        this.id = playerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(points, player.points) && Objects.equals(firstName, player.firstName) && Objects
                .equals(lastName, player.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, points, firstName, lastName);
    }
}
