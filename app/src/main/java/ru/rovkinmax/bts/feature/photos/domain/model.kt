package ru.rovkinmax.bts.feature.photos.domain

import java.io.Serializable

data class PhotoEntity(val id: String,
                       val title: String,
                       val urlThumbnail: String,
                       val url: String,
                       val urlLarge: String) : Serializable