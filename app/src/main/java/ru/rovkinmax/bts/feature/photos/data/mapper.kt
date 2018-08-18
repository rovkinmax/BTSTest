package ru.rovkinmax.bts.feature.photos.data

import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity

fun PhotoDto.toEntity(): PhotoEntity {
    return PhotoEntity(id.orEmpty(),
            title.orEmpty(),
            urlThumbnail = makePhotoUrl(farm.orEmpty(), server.orEmpty(), id.orEmpty(), secret.orEmpty(), PhotoDto.SIZE_THUMBNAIL),
            url = makePhotoUrl(farm.orEmpty(), server.orEmpty(), id.orEmpty(), secret.orEmpty(), PhotoDto.SIZE_MEDIUM),
            urlLarge = makePhotoUrl(farm.orEmpty(), server.orEmpty(), id.orEmpty(), secret.orEmpty(), PhotoDto.SIZE_LARGE))
}

private fun makePhotoUrl(farm: String, serverId: String, id: String, secret: String, size: String): String {
    return "https://farm$farm.staticflickr.com/$serverId/${id}_$secret.jpg"
}