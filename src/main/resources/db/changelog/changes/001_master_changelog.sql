-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE IF NOT EXISTS `animes` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`mal_id` VARCHAR(255) NOT NULL UNIQUE,
	`title` VARCHAR(255) NOT NULL,
	`url` VARCHAR(255) NOT NULL,
	`image_Url` VARCHAR(255) NOT NULL,
	`type` VARCHAR(255) NOT NULL,
	`nb_episodes` INTEGER NOT NULL,
	`storage_state` VARCHAR(255) NOT NULL,
	`is_complete` Boolean,
	`last_avaible_episode` INTEGER
);
CREATE TABLE IF NOT EXISTS `animeEpisodes` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`mal_id` VARCHAR(255) NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`episode_Number` INTEGER NOT NULL,
	`date_Sortie` TIMESTAMP WITH TIME ZONE,
	`url_Torrent` VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS `trackedanimetorrent` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`mal_id` INTEGER NOT NULL UNIQUE,
	`last_Episode_On_Server` INTEGER NOT NULL,
	`search_words` VARCHAR(255) NOT NULL,
	`day_of_release` VARCHAR(255) NOT NULL
);

ALTER TABLE IF EXISTS `trackedanimetorrent` ADD COLUMN IF NOT EXISTS `last_Episode_On_Server` INTEGER DEFAULT 0 NOT NULL AFTER `mal_id` ;

CREATE TABLE IF NOT EXISTS `animeepisodetorrent` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`mal_id` INTEGER,
	`episode_Number` INTEGER NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`date_Sortie` DATE,
	`torrent_link` VARCHAR(255) NOT NULL,
	`torrent_id` INTEGER,
	`torrent_size` VARCHAR(255) NOT NULL,
    	`seeders` INTEGER,
    	`leechers` INTEGER,
    	`completed` INTEGER
);

CREATE UNIQUE INDEX IF NOT EXISTS "PK_ANIME_EPISODE_TABLE" ON `animeEpisodes` (`mal_id`, `episode_Number`);