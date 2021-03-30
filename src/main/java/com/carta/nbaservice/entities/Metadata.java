package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Metadata(int totalPages, int currentPage, int nextPage, int perPage, int totalCount) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.perPage = perPage;
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
