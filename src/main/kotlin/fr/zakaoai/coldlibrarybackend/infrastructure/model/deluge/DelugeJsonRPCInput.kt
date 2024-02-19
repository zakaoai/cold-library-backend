package fr.zakaoai.coldlibrarybackend.infrastructure.model.deluge

data class DelugeJsonRPCInput(val method: String, val params: List<Any>, val id: Int)
