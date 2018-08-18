package ru.rovkinmax.bts.feature.photos.presentation.detail.view

import ru.rovkinmax.bts.feature.global.presentation.view.BaseView

interface PhotoDetailView : BaseView {
    fun showPhoto(url: String)
    fun showTitle(title: String)
}