CREATE TABLE IF NOT EXISTS `posts` (
    `id`         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `api_Id`        INTEGER  NOT NULL,
     `title` VARCHAR(255) NOT NULL,
     `body` VARCHAR(255) NOT NULL,
     `user_Id`        INTEGER  NOT NULL
);

CREATE TABLE IF NOT EXISTS `animes` (
    `id`         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `mal_id`        VARCHAR(255) NOT NULL,
    `title`        VARCHAR(255) NOT NULL,
     `url` VARCHAR(255) NOT NULL,
     `image_Url` VARCHAR(255) NOT NULL,
     `type` VARCHAR(255) NOT NULL,
     `episodes`        INTEGER  NOT NULL
);