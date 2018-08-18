package ru.rovkinmax.bts.feature.photos.presentation.search.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fmt_photo_search.*
import ru.rovkinmax.bts.R
import ru.rovkinmax.bts.di.DI
import ru.rovkinmax.bts.di.inject
import ru.rovkinmax.bts.feature.global.presentation.RxError
import ru.rovkinmax.bts.feature.global.presentation.view.BaseFragment
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.feature.photos.presentation.search.presenter.PhotoSearchPresenter
import ru.rovkinmax.bts.feature.photos.presentation.search.view.adapter.PhotosAdapter
import ru.rovkinmax.bts.feature.photos.presentation.search.view.adapter.SuggestionAdapter
import ru.rovkinmax.bts.model.system.rx.SchedulersProvider
import ru.rovkinmax.bts.model.ui.extentions.gone
import ru.rovkinmax.bts.model.ui.extentions.show
import ru.rovkinmax.bts.model.ui.pagination.paginationObservable
import toothpick.Toothpick
import javax.inject.Inject

class PhotoSearchFragment : BaseFragment(), PhotoSearchView, SearchView.OnQueryTextListener {

    companion object {
        private const val SEARCH_DEBOUNCE = 600L
        private const val SPAN_COUNT = 3
        fun newInstance(): PhotoSearchFragment {
            return PhotoSearchFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override val layoutRes: Int = R.layout.fmt_photo_search
    override val title: Int = R.string.search_title

    @InjectPresenter
    lateinit var presenter: PhotoSearchPresenter
    @Inject
    lateinit var schedulersProvider: SchedulersProvider
    private val adapter = PhotosAdapter(this::onItemClick)
    private val adapterSuggestion = SuggestionAdapter(this::onSelectSuggestion)
    private var paginationDisposable: Disposable? = null
    private val searchView: SearchView by lazy { activity!!.layoutInflater.inflate(R.layout.search_view, null) as SearchView }

    @ProvidePresenter
    fun providePresenter(): PhotoSearchPresenter {
        return Toothpick.openScope(DI.SCOPE_FLOW_PHOTOS).getInstance(PhotoSearchPresenter::class.java)
    }

    override fun injectDependencies() {
        Toothpick.openScope(DI.SCOPE_FLOW_PHOTOS).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        suggestionRecycler.layoutManager = LinearLayoutManager(activity)
        suggestionRecycler.adapter = adapterSuggestion
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.photo_search, menu)
        menu?.findItem(R.id.search)?.let { item ->
            item.actionView = searchView
            onSearchViewReady(searchView)
        }
    }

    private fun onSearchViewReady(searchView: SearchView) {
        searchView.setOnQueryTextListener(this)
    }

    private fun onSelectSuggestion(query: String) {
        onQueryTextSubmit(query)
        onHideKeyboard()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.onSearchInputSubmited(query.orEmpty())
        gSuggestion.gone()
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        presenter.onSearchInputChanged(query.orEmpty())
        return false
    }

    override fun showSuggestions(it: List<String>) {
        gSuggestion.show()
        adapterSuggestion.setList(it)
        adapterSuggestion.notifyDataSetChanged()
    }

    override fun showPhotoList(photoList: List<PhotoEntity>) {
        recyclerView.show()
        adapter.changeDataSet(photoList)
        restartPagination()
    }

    override fun onResume() {
        super.onResume()
        if (paginationDisposable != null)
            restartPagination()
    }

    private fun restartPagination() {
        if (paginationDisposable?.isDisposed == false)
            paginationDisposable?.dispose()
        paginationDisposable = providePaginationDisposable()
    }

    override fun showEmptyStub() {
        tvEmpty.show()
        recyclerView.gone()
    }

    override fun hideEmptyStub() {
        tvEmpty.gone()
    }

    private fun providePaginationDisposable(): Disposable {
        return recyclerView.paginationObservable(PhotoSearchPresenter.SIZE_PAGE)
                .subscribe(Consumer {
                    presenter.loadNewPageWithOffset(adapter.getRealItemCount())
                }, RxError.doNothing())
    }


    override fun showLoadingIndicator() {
        errorContainer.gone()
        tvEmpty.gone()
        progress.show()
    }

    override fun hideLoadingIndicator() {
        progress.gone()
    }

    override fun showPaginationLoading() {
        adapter.showLoading()
    }

    override fun hidePaginationLoading() {
        //do nothing
    }

    override fun showPageError(message: String) {
        adapter.showError(message) {
            presenter.loadNewPageWithOffset(adapter.getRealItemCount())
        }
    }

    override fun hidePageError() {
        adapter.hideError()
    }

    override fun showNewPagePhotos(photoList: List<PhotoEntity>) {
        adapter.addDataSet(photoList)
    }

    override fun showErrorMessage(message: String, needCallback: Boolean) {
        tvEmpty.gone()
        recyclerView.gone()
        errorContainer.show()
        tvError.text = message
        btnRetry.setOnClickListener { presenter.onRetryClick(searchView.query?.toString().orEmpty()) }
    }

    private fun onItemClick(photo: PhotoEntity) {
        presenter.onItemClick(photo)
    }

    override fun onBackPressed() = presenter.onBackPressed()
}