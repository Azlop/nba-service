package com.carta.nbaservice.dtos;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class TeamDto {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    public TeamDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeamDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TeamDto teamDto = (TeamDto) o;
        return Objects.equals(id, teamDto.id) && Objects.equals(name, teamDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
