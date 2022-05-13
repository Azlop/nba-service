package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
public class Team {

    private Integer id;
    private String abbreviation;
    private String city;
    private String conference;
    private String division;
    @JsonProperty("full_name")
    private String fullName;
    private String name;
}