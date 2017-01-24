
use sirqlate;
#SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS game_players;
DROP TABLE IF EXISTS player_achievements;
DROP TABLE IF EXISTS achievements_list;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS inventory; 
DROP TABLE IF EXISTS moves;
DROP TABLE IF EXISTS player;
#SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE game(
id integer(10) auto_increment,
game_date date,
primary key (id)
);

CREATE TABLE player(
id integer(10) auto_increment,
name varchar(25),
birthday date, 
email varchar(25) unique not null,
score integer(10) DEFAULT 0,
gamesPlayed integer(10) DEFAULT 0,
passwordHash varchar(100),
passwordSalt varchar(20),
primary key (id)
);

CREATE TABLE game_players(
game_id integer(10),
player_id integer(10)
);

CREATE TABLE items(
id integer(10) auto_increment,
name varchar(25) unique,
primary key (id)
);

CREATE TABLE inventory(
player_id integer(10),
item_id integer(10),
quantity integer(10)
);

CREATE TABLE player_achievements(
player_id integer(10),
achievement_id integer(10)
);


CREATE TABLE achievements_list(
id integer(10) auto_increment,
name varchar(25),
description varchar(25),
primary key (id)
);

CREATE TABLE moves(
id integer(10) auto_increment,
turn_num integer(10) not null,
game_id integer(10) not null, 
piece_num integer(10) not null,
player_id integer(10) not null,
start_cell integer(10) not null,
end_cell integer(10) not null,
points_awarded integer(10),
fight integer(1) not null, 
split integer(1) not null,
roll_result integer(10) not null, 
item_gained integer(10),
primary key (id)
);


## Disabling all FK constraints for now to simplify early development work

#ALTER TABLE game_players ADD FOREIGN KEY (game_id) REFERENCES game (id);

#ALTER TABLE game_players ADD FOREIGN KEY (player_id) REFERENCES player (id);

#ALTER TABLE inventory ADD FOREIGN KEY (player_id) REFERENCES player (id);

#ALTER TABLE inventory ADD FOREIGN KEY (item_id) REFERENCES items (id);

#ALTER TABLE moves ADD FOREIGN KEY (game_id) REFERENCES game (id);

#ALTER TABLE moves ADD FOREIGN KEY (player_id) REFERENCES player (id);

#ALTER TABLE moves ADD FOREIGN KEY (item_gained) REFERENCES items (id);

#ALTER TABLE player_achievements ADD FOREIGN KEY (player_id) REFERENCES player (id);

#ALTER TABLE player_achievements ADD FOREIGN KEY (achievement_id) REFERENCES achievement_list (id);

