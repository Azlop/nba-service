package com.carta.nbaservice.repos;

import org.springframework.data.repository.CrudRepository;

import com.carta.nbaservice.domain.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
