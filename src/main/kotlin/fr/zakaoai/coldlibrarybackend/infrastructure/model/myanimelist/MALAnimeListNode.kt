package fr.zakaoai.coldlibrarybackend.infrastructure.model.myanimelist


data class MALAnimeListNode(
    val id: Int,
    val title: String,
    val main_picture: MalPicture,
    val start_date: String?,
    val end_date: String?,
    val mean: Float,
    val rank: Int,
    val popularity: Int,
    val genres: List<MALGenre>,
    val media_type: String,
    val status: String,
    val num_episodes: Int,
    val start_season: MALSeason?,
    val broadcast: MALBroadcast?,
    val rating: String

)
