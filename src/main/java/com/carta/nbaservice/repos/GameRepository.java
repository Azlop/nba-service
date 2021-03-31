package com.carta.nbaservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carta.nbaservice.dtos.GameDto;

public interface GameRepository extends JpaRepository<GameDto, Integer> {
}
