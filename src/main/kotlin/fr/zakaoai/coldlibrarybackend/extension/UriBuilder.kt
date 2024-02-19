package fr.zakaoai.coldlibrarybackend.extension

import org.springframework.web.util.UriBuilder

fun UriBuilder.queryParamNotNull(n: String, v: String?): UriBuilder = v?.let { this.queryParam(n, it) } ?: this