package com.carta.nbaservice.dtos;

import lombok.Builder;

@Builder
public record CommentDto(Integer gameId, String text) {
}