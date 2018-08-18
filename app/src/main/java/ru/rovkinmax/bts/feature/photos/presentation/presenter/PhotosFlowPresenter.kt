package ru.rovkinmax.bts.feature.photos.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import ru.rovkinmax.bts.Screens
import ru.rovkinmax.bts.feature.global.presentation.presenter.Presenter
import ru.rovkinmax.bts.feature.photos.presentation.view.PhotosFlowView
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import javax.inject.Inject

@InjectViewState
class PhotosFlowPresenter @Inject constructor(private val router: FlowRouter) : Presenter<PhotosFlowView>(router) {

    override fun onFirstViewAttach() {
        router.newRootScreen(Screens.SCREEN_PHOTO_SEARCH)
    }
}