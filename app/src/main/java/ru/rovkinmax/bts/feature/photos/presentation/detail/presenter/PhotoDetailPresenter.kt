package ru.rovkinmax.bts.feature.photos.presentation.detail.presenter

import com.arellomobile.mvp.InjectViewState
import ru.rovkinmax.bts.feature.global.presentation.presenter.Presenter
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.feature.photos.presentation.detail.view.PhotoDetailView
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import javax.inject.Inject

@InjectViewState
class PhotoDetailPresenter @Inject constructor(private val photoEntity: PhotoEntity,
                                               router: FlowRouter) : Presenter<PhotoDetailView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showTitle(photoEntity.title)
        viewState.showPhoto(photoEntity.urlLarge)
    }
}