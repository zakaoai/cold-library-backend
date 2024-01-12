package fr.zakaoai.coldlibrarybackend.model.dto.response

import java.time.DayOfWeek

class TrackedAnimeTorrentDTO(
    var malId: Int,
    var lastEpisodeOnServer: Int,
    var searchWords: String,
    var dayOfRelease: DayOfWeek,
)