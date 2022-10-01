package com.shrutii.melodrama.models

import java.io.Serializable

data class SongModel(
    val Song: String? = null,
    val Artist: String? = null,
    val Movie: String? = null,
    val Released: Long? = null,
    val Genre: String? = null,
    val Links: String? = null,
    val Vibe: String? = null,
    val Image: String? = null
) : Serializable
