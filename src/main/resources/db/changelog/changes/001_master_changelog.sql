-- liquibase formatted sql

-- changeset liquibase:1
-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 1.0.6
-- PostgreSQL version: 16.0
-- Project Site: pgmodeler.io
-- Model Author: ---
-- ddl-end --

-- object: cold_library."Log" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."Log" CASCADE;
CREATE TABLE cold_library."Log" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 1 ),
	action character varying NOT NULL,
	date timestamp NOT NULL DEFAULT now(),
	id_user bigint,
	CONSTRAINT "Logs_pk" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE cold_library."Log" OWNER TO postgres;
-- ddl-end --

-- -- object: cold_library."Log_id_seq" | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS cold_library."Log_id_seq" CASCADE;
-- CREATE SEQUENCE cold_library."Log_id_seq"
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 9223372036854775807
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE cold_library."Log_id_seq" OWNER TO postgres;
-- -- ddl-end --
--
-- object: cold_library."User" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."User" CASCADE;
CREATE TABLE cold_library."User" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 1 ),
	sub character varying NOT NULL,
	name character varying NOT NULL,
	username character varying NOT NULL,
	email character varying NOT NULL,
	mal_username character varying NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE cold_library."User" OWNER TO postgres;
-- ddl-end --

-- -- object: cold_library."User_id_seq" | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS cold_library."User_id_seq" CASCADE;
-- CREATE SEQUENCE cold_library."User_id_seq"
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 9223372036854775807
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE cold_library."User_id_seq" OWNER TO postgres;
-- -- ddl-end --
--
-- object: cold_library."Request" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."Request" CASCADE;
CREATE TABLE cold_library."Request" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 1 ),
	type character varying NOT NULL,
	state character varying NOT NULL,
	id_user bigint NOT NULL,
	mal_id bigint,
	date timestamp NOT NULL DEFAULT now(),
	CONSTRAINT "Request_pk" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE cold_library."Request" OWNER TO postgres;
-- ddl-end --

-- -- object: cold_library."Request_id_seq" | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS cold_library."Request_id_seq" CASCADE;
-- CREATE SEQUENCE cold_library."Request_id_seq"
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 9223372036854775807
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE cold_library."Request_id_seq" OWNER TO postgres;
-- -- ddl-end --
--
-- object: cold_library."Anime" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."Anime" CASCADE;
CREATE TABLE cold_library."Anime" (
	mal_id bigint NOT NULL,
	mal_url character varying,
	mal_img character varying,
	title character varying NOT NULL,
	type character varying,
	episodes smallint,
	status character varying,
	score double precision,
	season character varying,
	year integer,
	broadcast character varying,
	rank smallint,
	CONSTRAINT "Anime_pk" PRIMARY KEY (mal_id)
);
-- ddl-end --
ALTER TABLE cold_library."Anime" OWNER TO postgres;
-- ddl-end --

-- object: cold_library."AnimeEpisode" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."AnimeEpisode" CASCADE;
CREATE TABLE cold_library."AnimeEpisode" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 1 ),
	episode_number smallint NOT NULL,
	title character varying,
	url character varying,
	mal_id bigint NOT NULL,
	date timestamp,
	CONSTRAINT "AnimeEpisode_pk" PRIMARY KEY (id),
	CONSTRAINT "AnimeEpisode_uq" UNIQUE (episode_number,mal_id)
);
-- ddl-end --
ALTER TABLE cold_library."AnimeEpisode" OWNER TO postgres;
-- ddl-end --

-- -- object: cold_library."AnimeEpisode_id_seq" | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS cold_library."AnimeEpisode_id_seq" CASCADE;
-- CREATE SEQUENCE cold_library."AnimeEpisode_id_seq"
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 9223372036854775807
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE cold_library."AnimeEpisode_id_seq" OWNER TO postgres;
-- -- ddl-end --
--
-- object: cold_library."AnimeInServer" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."AnimeInServer" CASCADE;
CREATE TABLE cold_library."AnimeInServer" (
	storage_state character varying NOT NULL,
	is_downloading boolean NOT NULL,
	is_complete boolean NOT NULL,
	last_avaible_episode smallint,
	added_on_server timestamp DEFAULT now(),
	mal_id bigint NOT NULL,
	CONSTRAINT "AnimeInServer_pk" PRIMARY KEY (mal_id)
);
-- ddl-end --
ALTER TABLE cold_library."AnimeInServer" OWNER TO postgres;
-- ddl-end --

-- object: cold_library."AnimeTorrent" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."AnimeTorrent" CASCADE;
CREATE TABLE cold_library."AnimeTorrent" (
	last_episode_on_server smallint NOT NULL,
	search_words character varying NOT NULL,
	day_of_release character varying NOT NULL,
	delta_episode smallint NOT NULL DEFAULT 0,
	torrent_path character varying NOT NULL,
	mal_id bigint NOT NULL,
	CONSTRAINT "AnimeTorrent_pk" PRIMARY KEY (mal_id)
);
-- ddl-end --
ALTER TABLE cold_library."AnimeTorrent" OWNER TO postgres;
-- ddl-end --

-- object: cold_library."AnimeEpisodeTorrent" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."AnimeEpisodeTorrent" CASCADE;
CREATE TABLE cold_library."AnimeEpisodeTorrent" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 1 ),
	title character varying NOT NULL,
	date date NOT NULL,
	torrent_link character varying NOT NULL,
	torrent_id bigint NOT NULL,
	torrent_size character varying NOT NULL,
	seeders smallint NOT NULL,
	leechers smallint NOT NULL,
	completed smallint NOT NULL,
	"id_AnimeEpisode" bigint,
	mal_id bigint,
	CONSTRAINT "AnimeEpisodeTorrent_pk" PRIMARY KEY (id),
	CONSTRAINT "AnimeEpisodeTorrent_uq" UNIQUE ("id_AnimeEpisode")
);
-- ddl-end --
ALTER TABLE cold_library."AnimeEpisodeTorrent" OWNER TO postgres;
-- ddl-end --

-- -- object: cold_library."AnimeEpisodeTorrent_id_seq" | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS cold_library."AnimeEpisodeTorrent_id_seq" CASCADE;
-- CREATE SEQUENCE cold_library."AnimeEpisodeTorrent_id_seq"
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 9223372036854775807
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE cold_library."AnimeEpisodeTorrent_id_seq" OWNER TO postgres;
-- -- ddl-end --
--
-- object: cold_library."DelugeEpisodeTorrent" | type: TABLE --
-- DROP TABLE IF EXISTS cold_library."DelugeEpisodeTorrent" CASCADE;
CREATE TABLE cold_library."DelugeEpisodeTorrent" (
	id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 1 ),
	torrent_hash character varying NOT NULL,
	progress real,
	"id_AnimeEpisodeTorrent" bigint,
	CONSTRAINT "DelugeEpisodeTorrent_pk" PRIMARY KEY (id),
	CONSTRAINT "DelugeEpisodeTorrent_uq" UNIQUE ("id_AnimeEpisodeTorrent")
);
-- ddl-end --
ALTER TABLE cold_library."DelugeEpisodeTorrent" OWNER TO postgres;
-- ddl-end --

-- -- object: cold_library."DelugeEpisodeTorrent_id_seq" | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS cold_library."DelugeEpisodeTorrent_id_seq" CASCADE;
-- CREATE SEQUENCE cold_library."DelugeEpisodeTorrent_id_seq"
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 9223372036854775807
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE cold_library."DelugeEpisodeTorrent_id_seq" OWNER TO postgres;
-- -- ddl-end --
--
-- object: user_fk | type: CONSTRAINT --
-- ALTER TABLE cold_library."Log" DROP CONSTRAINT IF EXISTS user_fk CASCADE;
ALTER TABLE cold_library."Log" ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
REFERENCES cold_library."User" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: user_fk | type: CONSTRAINT --
-- ALTER TABLE cold_library."Request" DROP CONSTRAINT IF EXISTS user_fk CASCADE;
ALTER TABLE cold_library."Request" ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
REFERENCES cold_library."User" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Anime_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."Request" DROP CONSTRAINT IF EXISTS "Anime_fk" CASCADE;
ALTER TABLE cold_library."Request" ADD CONSTRAINT "Anime_fk" FOREIGN KEY (mal_id)
REFERENCES cold_library."Anime" (mal_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Anime_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."AnimeEpisode" DROP CONSTRAINT IF EXISTS "Anime_fk" CASCADE;
ALTER TABLE cold_library."AnimeEpisode" ADD CONSTRAINT "Anime_fk" FOREIGN KEY (mal_id)
REFERENCES cold_library."Anime" (mal_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Anime_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."AnimeInServer" DROP CONSTRAINT IF EXISTS "Anime_fk" CASCADE;
ALTER TABLE cold_library."AnimeInServer" ADD CONSTRAINT "Anime_fk" FOREIGN KEY (mal_id)
REFERENCES cold_library."Anime" (mal_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Anime_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."AnimeTorrent" DROP CONSTRAINT IF EXISTS "Anime_fk" CASCADE;
ALTER TABLE cold_library."AnimeTorrent" ADD CONSTRAINT "Anime_fk" FOREIGN KEY (mal_id)
REFERENCES cold_library."Anime" (mal_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "AnimeEpisode_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."AnimeEpisodeTorrent" DROP CONSTRAINT IF EXISTS "AnimeEpisode_fk" CASCADE;
ALTER TABLE cold_library."AnimeEpisodeTorrent" ADD CONSTRAINT "AnimeEpisode_fk" FOREIGN KEY ("id_AnimeEpisode")
REFERENCES cold_library."AnimeEpisode" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "AnimeTorrent_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."AnimeEpisodeTorrent" DROP CONSTRAINT IF EXISTS "AnimeTorrent_fk" CASCADE;
ALTER TABLE cold_library."AnimeEpisodeTorrent" ADD CONSTRAINT "AnimeTorrent_fk" FOREIGN KEY (mal_id)
REFERENCES cold_library."AnimeTorrent" (mal_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "AnimeEpisodeTorrent_fk" | type: CONSTRAINT --
-- ALTER TABLE cold_library."DelugeEpisodeTorrent" DROP CONSTRAINT IF EXISTS "AnimeEpisodeTorrent_fk" CASCADE;
ALTER TABLE cold_library."DelugeEpisodeTorrent" ADD CONSTRAINT "AnimeEpisodeTorrent_fk" FOREIGN KEY ("id_AnimeEpisodeTorrent")
REFERENCES cold_library."AnimeEpisodeTorrent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "grant_CU_26541e8cda" | type: PERMISSION --
GRANT CREATE,USAGE
   ON SCHEMA public
   TO pg_database_owner;
-- ddl-end --

-- object: "grant_U_cd8e46e7b6" | type: PERMISSION --
GRANT USAGE
   ON SCHEMA public
   TO PUBLIC;
-- ddl-end --
