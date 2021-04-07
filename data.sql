-- GAMES
insert
into game
    (away_team_name, away_team_score, date, game_id, home_team_name, home_team_score, id)
values ('awayTeam1', 60, '2021-03-28', 10, 'homeTeam1', 50, 1);
insert
into game
    (away_team_name, away_team_score, date, game_id, home_team_name, home_team_score, id)
values ('awayTeam2', 70, '2021-03-28', 20, 'homeTeam2', 60, 2);
insert
into game
    (away_team_name, away_team_score, date, game_id, home_team_name, home_team_score, id)
values ('awayTeam3', 80, '2021-03-28', 30, 'homeTeam3', 100, 3);
insert
into game
    (away_team_name, away_team_score, date, game_id, home_team_name, home_team_score, id)
values ('awayTeam4', 90, '2021-03-28', 40, 'homeTeam4', 110, 4);

-- COMMENTS
insert
into comment
    (comment_id, game_id, text, timestamp)
values (1, 1, 'some comment', '2021-03-28 19:48:46.378530');
insert
into comment
    (comment_id, game_id, text, timestamp)
values (2, 1, 'another comment', '2021-03-28 19:50:46.378530');

-- PLAYERS
insert
into player
    (id, first_name, last_name)
values (1, 'name1', 'last1');
insert
into player
    (id, first_name, last_name)
values (2, 'name2', 'last2');
insert
into player
    (id, first_name, last_name)
values (3, 'name3', 'last3');

-- PLAYERS POINTS
insert
into player_points
    (game_id, player_id, points)
values (1, 1, 13);
insert
into player_points
    (game_id, player_id, points)
values (1, 2, 32);
insert
into player_points
    (game_id, player_id, points)
values (1, 3, 5);