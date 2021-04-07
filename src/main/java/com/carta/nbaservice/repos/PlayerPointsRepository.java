package com.carta.nbaservice.repos;

import org.springframework.data.repository.CrudRepository;

import com.carta.nbaservice.domain.GamePlayerPK;
import com.carta.nbaservice.domain.PlayerPoints;

public interface PlayerPointsRepository extends CrudRepository<PlayerPoints, GamePlayerPK> {
}
