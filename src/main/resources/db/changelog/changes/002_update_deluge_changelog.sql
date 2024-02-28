ALTER TABLE cold_library."DelugeEpisodeTorrent" ADD COLUMN torrent_id integer;

UPDATE cold_library."DelugeEpisodeTorrent" d SET torrent_id = (SELECT torrent_id FROM cold_library."AnimeEpisodeTorrent" a WHERE a.id = d.id_anime_episode_torrent );

ALTER TABLE cold_library."DelugeEpisodeTorrent" ALTER COLUMN torrent_id SET NOT NULL;