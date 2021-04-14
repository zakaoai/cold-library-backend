CREATE TABLE IF NOT EXISTS `posts` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`api_Id` INTEGER NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`body` VARCHAR(255) NOT NULL,
	`user_Id` INTEGER NOT NULL
);
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
	`search_words` VARCHAR(255) NOT NULL,
	`day_of_release` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `animeepisodetorrent` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`mal_id` INTEGER,
	`episode_Number` INTEGER NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`date_Sortie` DATE,
	`torrent_link` VARCHAR(255) NOT NULL,
	`torrent_id` INTEGER
);
CREATE UNIQUE INDEX IF NOT EXISTS "PK_ANIME_EPISODE_TABLE" ON `animeEpisodes` (`mal_id`, `episode_Number`);