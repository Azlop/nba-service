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
public class Metadata {

    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("next_page")
    private int nextPage;
    @JsonProperty("per_page")
    private int perPage;
    @JsonProperty("total_count")
    private int totalCount;
}