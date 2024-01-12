# Liste des fonctionnalités

Mise en place d'une feature de request :

Workflow avec collones pour les admin en drag and drop
Vision de ses propres request pour l'utilisateur connecté

Tableaux des requests avec le nombres de fois ou c'est request

Différent type de request : Move to Hot et Ajouter au Serveur

Différents état : Waiting for Approval, View, Done

Rechercher des animes
Ajouter des animes au hot / cold
Ajouter des anime en attente

Onglet Seasonal anime avec une vue par saison et année

Liste anime
Voir la liste des animes avec plusieurs mode de vue : ( par saison / par alphabet, par date d'ajout au serveur)


Torrent
Voir les anime en torrent
Choisir le torrent parmis une liste récupéré depuis nyaa
Rechercher tout les épisodes
Rechercher seulement le suivant

Profile utilisateur
voir son profile / role
voir son anime list si l'username myanimelist est saisie
Mettre à jour son user my anime list

Peu être dans le futur :
Intégration avec Emby
Intégration avec MyAnimeList et le token utilisateur pour gerer l'anime liste de l'utilisateur



Refonte BDD :

Logs:
id
userId
action
date

User :
id
sub
name
username
email
myanimelistUsername

Request :
id
malId
type
state
userId

Anime :
id
malId
malUrl
malImg
malImgLarge
Titre
Type
episodes
status
score
season
year
broadcast
rank

AnimeEpisode :
id
malId
episodeNumber
title
url

AnimeInServer :
id
malId
storageState
isDownloading
isComplete
lastAvaibleEpisode
addedOnServer (date)

AnimeTorrent :
malId
lastEpisodeOnServer
searchWords
dayOfRelease
deltaEpisode
torrentPath

AnimeEpisodeTorrent :
id
animeEpisodeId
title
date
torrentLink
torrentId
torrentSize
seeders
leechers
completed

DelugeEpisodeTorrent :
id
animeEpisodeTorrentId
torrent_hash
progress

Voir le potentiel de : https://micronaut-projects.github.io/micronaut-data/latest/guide/#spring

JSON RPC Add Torrent Exemple : http://192.168.1.15:8113/json
```JSON
{
"method": "core.add_torrent_url",
"params": [
"https://nyaa.si/download/1756718.torrent",
{
"download_location": "/downloads/One Piece",
}
],
"id": 1194
}
```

result :
```JSON
{
    "result": "61300ec8ab682922ff494b6011cbdd4be32c2b31",
    "error": null,
    "id": 1194
}
```

JSON RPC Get Torrent Info :
```JSON
{
    "method": "core.get_torrent_status",
    "params": [
        "61300ec8ab682922ff494b6011cbdd4be32c2b31",
        ["hash","download_location","progress"]
    ],
    "id": 1194
}
```
result :
```JSON
{
    "result": {
        "download_location": "/downloads/One Piece",
        "hash": "61300ec8ab682922ff494b6011cbdd4be32c2b31",
        "progress": 100.0
    },
    "error": null,
    "id": 1194
}
```
JSON RPC Get Multiple Torrent Info :
```JSON
{
    "method": "core.get_torrents_status",
    "params": [
        {
            "hash": [
                "87c7c5654f3d2f62ee1f4459a4b742bf810ae463", "61300ec8ab682922ff494b6011cbdd4be32c2b31"
            ]
        },
        [
            "hash",
            "download_location",
            "progress"
        ]
    ],
    "id": 1194
}
```
result:
```JSON
{
    "result": {
        "87c7c5654f3d2f62ee1f4459a4b742bf810ae463": {
            "download_location": "/downloads/Devel Line",
            "hash": "87c7c5654f3d2f62ee1f4459a4b742bf810ae463",
            "progress": 0.0
        },
        "61300ec8ab682922ff494b6011cbdd4be32c2b31": {
            "download_location": "/downloads/One Piece",
            "hash": "61300ec8ab682922ff494b6011cbdd4be32c2b31",
            "progress": 100.0
        }
    },
    "error": null,
    "id": 1194
}
```
User Anime list url : https://api.myanimelist.net/v2/users/zakaoai/animelist?limit=2&offset=100
avec le header X-MAL-CLIENT-ID

result : 
```JSON
{
    "data": [
        {
            "node": {
                "id": 14345,
                "title": "Btooom!",
                "main_picture": {
                    "medium": "https://cdn.myanimelist.net/images/anime/4/40977.jpg",
                    "large": "https://cdn.myanimelist.net/images/anime/4/40977l.jpg"
                }
            }
        },
        {
            "node": {
                "id": 48776,
                "title": "Build Divide: Code Black",
                "main_picture": {
                    "medium": "https://cdn.myanimelist.net/images/anime/1014/117452.jpg",
                    "large": "https://cdn.myanimelist.net/images/anime/1014/117452l.jpg"
                }
            }
        }
    ],
    "paging": {
        "previous": "https://api.myanimelist.net/v2/users/zakaoai/animelist?offset=98&limit=2",
        "next": "https://api.myanimelist.net/v2/users/zakaoai/animelist?offset=102&limit=2"
    }
}
```

https://www.bezkoder.com/spring-boot-r2dbc-postgresql/