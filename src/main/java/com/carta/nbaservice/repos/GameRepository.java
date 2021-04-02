package com.carta.nbaservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carta.nbaservice.domain.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
