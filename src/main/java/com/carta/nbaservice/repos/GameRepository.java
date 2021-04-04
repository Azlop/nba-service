package com.carta.nbaservice.repos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.carta.nbaservice.domain.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findByDate(LocalDate date);
}
