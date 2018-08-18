package ru.rovkinmax.bts.feature.photos.presentation.search.presenter

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.rovkinmax.bts.feature.global.TestErrorhandler
import ru.rovkinmax.bts.feature.global.presentation.ErrorHandler
import ru.rovkinmax.bts.feature.photos.domain.PhotoInteractor
import ru.rovkinmax.bts.feature.photos.presentation.search.view.PhotoSearchView
import ru.rovkinmax.bts.feature.photos.presentation.search.view.`PhotoSearchView$$State`
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import ru.rovkinmax.bts.model.system.rx.TestSchedulers

@RunWith(MockitoJUnitRunner::class)
class PhotoSearchPresenterTest {


    @Mock
    lateinit var view: PhotoSearchView

    @Mock
    lateinit var searchViewState: `PhotoSearchView$$State`

    @Mock
    lateinit var router: FlowRouter

    @Mock
    lateinit var interactor: PhotoInteractor

    private val errorHandler: ErrorHandler = spy(TestErrorhandler())

    private lateinit var presenter: PhotoSearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = spy(PhotoSearchPresenter(router, interactor, errorHandler, TestSchedulers()).apply {
            setViewState(searchViewState)
            attachView(view)
        })
    }

    @Test
    fun testOnSearchInputSubmitedEmptyInput() {
        presenter.onSearchInputSubmited("")
        verifyZeroInteractions(interactor)
        verifyZeroInteractions(errorHandler)
    }

    @Test
    fun testOnSearchInputSubmitedWithEmptyResult() {
        whenever(interactor.search(any(), eq(30), eq(0))).doReturn(Single.just(emptyList()))
        presenter.onSearchInputSubmited("first")
        verify(presenter).lifecycle<Any>()
        verify(searchViewState).showLoadingIndicator()
        verify(searchViewState).hideLoadingIndicator()
        verify(errorHandler).proceed(searchViewState)
        verify(searchViewState).showEmptyStub()

        clearInvocations(searchViewState)
        clearInvocations(errorHandler)
        clearInvocations(presenter)

        presenter.onSearchInputSubmited("first")
        verifyZeroInteractions(searchViewState)
        verifyZeroInteractions(errorHandler)
        verify(presenter).onSearchInputSubmited("first")
        verifyNoMoreInteractions(presenter)
    }


    @Test
    fun testOnSearchInputSubmited() {
        whenever(interactor.search(any(), eq(30), eq(0))).doReturn(Single.just(arrayListOf(mock())))
        presenter.onSearchInputSubmited("first")
        verify(presenter).lifecycle<Any>()
        verify(errorHandler).proceed(searchViewState)
        verify(searchViewState).showLoadingIndicator()
        verify(searchViewState).hideLoadingIndicator()
        verify(searchViewState).hideEmptyStub()
        verify(searchViewState).showPhotoList(any())

        clearInvocations(searchViewState)
        clearInvocations(errorHandler)
        clearInvocations(presenter)

        presenter.onSearchInputSubmited("first")
        verifyZeroInteractions(searchViewState)
        verifyZeroInteractions(errorHandler)
        verify(presenter).onSearchInputSubmited("first")
        verifyNoMoreInteractions(presenter)
    }

    @Test()
    fun testLoadNewPageWithOffsetWithNullLastQuery() {
        presenter.loadNewPageWithOffset(0)

        verify(searchViewState).hidePageError()
        verifyZeroInteractions(errorHandler)
    }

    @Test
    fun testLoadNewPageWithOffset() {
        whenever(interactor.search(any(), eq(30), eq(0))).doReturn(Single.just(arrayListOf(mock())))
        presenter.onSearchInputSubmited("first")
        clearInvocations(presenter)
        clearInvocations(searchViewState)
        clearInvocations(errorHandler)

        whenever(interactor.search(eq("first"), eq(30), eq(1))).doReturn(Single.just(arrayListOf(mock())))
        presenter.loadNewPageWithOffset(1)
        verify(searchViewState).showPaginationLoading()
        verify(searchViewState).hidePaginationLoading()
        verify(searchViewState).showNewPagePhotos(any())
    }

    @Test
    fun testBackPressed() {
        presenter.onBackPressed()
        verify(router).back()
    }
}