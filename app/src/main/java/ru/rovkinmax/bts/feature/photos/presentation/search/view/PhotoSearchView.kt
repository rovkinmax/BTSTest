package ru.rovkinmax.bts.feature.photos.presentation.search.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.rovkinmax.bts.feature.global.presentation.view.BaseView
import ru.rovkinmax.bts.feature.global.presentation.view.EmptyView
import ru.rovkinmax.bts.feature.global.presentation.view.PaginationView
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity

@StateStrategyType(OneExecutionStateStrategy::class)
interface PhotoSearchView : BaseView, PaginationView, EmptyView {
    fun showPhotoList(photoList: List<PhotoEntity>)
    fun showPageError(message: String)
    fun hidePageError()
    fun showNewPagePhotos(photoList: List<PhotoEntity>)
    fun showSuggestions(it: List<String>)
}