package ru.rovkinmax.bts.feature.photos.presentation.search.presenter

import com.arellomobile.mvp.InjectViewState
import ru.rovkinmax.bts.Screens
import ru.rovkinmax.bts.feature.global.presentation.ErrorHandler
import ru.rovkinmax.bts.feature.global.presentation.RxDecor
import ru.rovkinmax.bts.feature.global.presentation.RxError
import ru.rovkinmax.bts.feature.global.presentation.errorView
import ru.rovkinmax.bts.feature.global.presentation.presenter.Presenter
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.feature.photos.domain.PhotoInteractor
import ru.rovkinmax.bts.feature.photos.presentation.search.view.PhotoSearchView
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import ru.rovkinmax.bts.model.system.rx.SchedulersProvider
import ru.rovkinmax.bts.model.system.rx.subscribe
import javax.inject.Inject

@InjectViewState
class PhotoSearchPresenter @Inject constructor(private val router: FlowRouter,
                                               private val interactor: PhotoInteractor,
                                               private val errorHandler: ErrorHandler,
                                               private val schedulersProvider: SchedulersProvider) : Presenter<PhotoSearchView>(router) {
    companion object {
        const val SIZE_PAGE = 30
    }

    private val pageErrorView = errorView({ viewState.showPageError(it) }, { viewState.hidePageError() })
    private var lastQuery: String? = null

    fun onRetryClick(query: String) {
        lastQuery = null
        onSearchInputSubmited(query)
    }


    fun onSearchInputChanged(query: String) {
        interactor.getSuggestionsForQuery(query)
                .observeOn(schedulersProvider.main())
                .compose(lifecycle())
                .subscribe(this::dispatchSuggestions, RxError.doNothing())
    }

    private fun dispatchSuggestions(it: List<String>) {
        viewState.showSuggestions(it)
    }

    fun onSearchInputSubmited(query: String) {
        if (query.isEmpty() || lastQuery == query) return
        lastQuery = query
        interactor.search(query, SIZE_PAGE, 0)
                .observeOn(schedulersProvider.main())
                .compose(lifecycle())
                .compose(RxDecor.loading(viewState))
                .subscribe(this::dispatchSearchResult, errorHandler.proceed(viewState))
    }

    private fun dispatchSearchResult(list: List<PhotoEntity>) {
        if (list.isEmpty())
            viewState.showEmptyStub()
        else {
            viewState.hideEmptyStub()
            viewState.showPhotoList(list)
        }
    }

    fun loadNewPageWithOffset(realItemCount: Int) {
        viewState.hidePageError()
        if (lastQuery != null) {
            interactor.search(lastQuery.orEmpty(), SIZE_PAGE, realItemCount)
                    .observeOn(schedulersProvider.main())
                    .compose(lifecycle())
                    .compose(RxDecor.paginationLoading(viewState))
                    .subscribe({ viewState.showNewPagePhotos(it) }, errorHandler.proceed(pageErrorView))

        }
    }

    fun onItemClick(photo: PhotoEntity) {
        router.navigateTo(Screens.SCREEN_PHOTO_DETAIL, photo)
    }
}