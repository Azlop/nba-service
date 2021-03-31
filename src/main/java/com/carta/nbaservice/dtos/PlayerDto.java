package com.carta.nbaservice.dtos;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class PlayerDto {

    @Id
    @GeneratedValue
    private Integer playerId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private int points;

    public PlayerDto(String firstName, String lastName, int points) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }

    public PlayerDto() {}

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlayerDto playerDto = (PlayerDto) o;
        return points == playerDto.points && Objects.equals(playerId, playerDto.playerId) && Objects.equals(firstName, playerDto.firstName) && Objects
                .equals(lastName, playerDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, firstName, lastName, points);
    }
}
