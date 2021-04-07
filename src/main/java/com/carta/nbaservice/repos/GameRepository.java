package com.carta.nbaservice.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.dtos.GamePointsDto;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findByDate(LocalDate date);

    @Query(value = "select player.first_name as firstName, player.last_name as lastName, player_points.points\n" + "from game\n"
            + "left outer join player_points on game.id = player_points.game_id\n" + "inner join player on player_points.player_id = player.id\n"
            + "where game.id = 1", nativeQuery = true)
    List<GamePointsDto> getPlayersPointsByGameId(@Param("gameId") Integer gameId);
}
