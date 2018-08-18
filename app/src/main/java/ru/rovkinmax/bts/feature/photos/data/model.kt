package ru.rovkinmax.bts.feature.photos.data

import ru.rovkinmax.bts.feature.global.data.BaseResponse

data class PhotoDto(
        val id: String?,
        val title: String?,
        val secret: String?,
        val server: String?,
        val farm: String?) {
    companion object {
        const val SIZE_THUMBNAIL = "_t"
        const val SIZE_MEDIUM = ""
        const val SIZE_LARGE = "_h"

    }
}

data class SearchResponse(val photos: PhotoResponse) : BaseResponse()
data class PhotoResponse(val photo: List<PhotoDto>)