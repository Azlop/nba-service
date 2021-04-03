package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    private Integer id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("height_feet")
    private int heightFeet;
    @JsonProperty("height_inches")
    private int heightInches;
    @JsonProperty("last_name")
    private String lastName;
    private String position;
    @JsonProperty("team_id")
    private int teamId;
    @JsonProperty("weight_pounds")
    private int weightPounds;

    public Player(Integer id, String firstName, int heightFeet, int heightInches, String lastName, String position, int teamId, int weightPounds) {
        this.id = id;
        this.firstName = firstName;
        this.heightFeet = heightFeet;
        this.heightInches = heightInches;
        this.lastName = lastName;
        this.position = position;
        this.teamId = teamId;
        this.weightPounds = weightPounds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(int heightFeet) {
        this.heightFeet = heightFeet;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(int heightInches) {
        this.heightInches = heightInches;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getWeightPounds() {
        return weightPounds;
    }

    public void setWeightPounds(int weightPounds) {
        this.weightPounds = weightPounds;
    }
}
