package ru.rovkinmax.bts.feature.photos.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.rovkinmax.bts.Screens
import ru.rovkinmax.bts.di.DI
import ru.rovkinmax.bts.di.inject
import ru.rovkinmax.bts.di.moduleFlow
import ru.rovkinmax.bts.feature.global.FlowFragment
import ru.rovkinmax.bts.feature.photos.data.PhotoRepository
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.feature.photos.domain.PhotoInteractor
import ru.rovkinmax.bts.feature.photos.presentation.detail.presenter.PhotoDetailPresenter
import ru.rovkinmax.bts.feature.photos.presentation.detail.view.PhotoDetailFragment
import ru.rovkinmax.bts.feature.photos.presentation.presenter.PhotosFlowPresenter
import ru.rovkinmax.bts.feature.photos.presentation.search.presenter.PhotoSearchPresenter
import ru.rovkinmax.bts.feature.photos.presentation.search.view.PhotoSearchFragment
import ru.rovkinmax.bts.feature.photos.presentation.view.PhotosFlowView
import ru.rovkinmax.bts.model.system.flow.FlowNavigator
import ru.rovkinmax.bts.model.system.flow.GlobalRouter
import toothpick.Toothpick

class PhotosFlowFragment : FlowFragment(), PhotosFlowView {

    companion object {
        fun newInstance(): PhotosFlowFragment {
            return PhotosFlowFragment().apply {
                arguments = Bundle()
            }
        }
    }

    @InjectPresenter
    lateinit var presenter: PhotosFlowPresenter

    @ProvidePresenter
    fun providePresenter(): PhotosFlowPresenter {
        return Toothpick.openScope(DI.SCOPE_FLOW_PHOTOS).getInstance(PhotosFlowPresenter::class.java)
    }


    override fun provideNavigator(router: GlobalRouter): FlowNavigator {
        return object : FlowNavigator(fragment = this, router = router) {

            override fun createFragment(screenKey: String?, data: Any?): Fragment? {
                return when (screenKey) {
                    Screens.SCREEN_PHOTO_SEARCH -> PhotoSearchFragment.newInstance()
                    Screens.SCREEN_PHOTO_DETAIL -> PhotoDetailFragment.newInstance(data as PhotoEntity)
                    else -> null
                }
            }
        }
    }

    override fun injectDependencies() {
        Toothpick.openScopes(DI.SCOPE_APP, DI.SCOPE_FLOW_PHOTOS).moduleFlow {
            bind(PhotoRepository::class.java).singletonInScope()
            bind(PhotoInteractor::class.java).singletonInScope()
            bind(PhotosFlowPresenter::class.java)
            bind(PhotoSearchPresenter::class.java)
            bind(PhotoDetailPresenter::class.java)
        }.inject(this)
    }
}