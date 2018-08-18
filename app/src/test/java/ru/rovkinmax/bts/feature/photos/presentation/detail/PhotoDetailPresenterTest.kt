package ru.rovkinmax.bts.feature.photos.presentation.detail

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.feature.photos.presentation.detail.presenter.PhotoDetailPresenter
import ru.rovkinmax.bts.feature.photos.presentation.detail.view.PhotoDetailView
import ru.rovkinmax.bts.feature.photos.presentation.detail.view.`PhotoDetailView$$State`
import ru.rovkinmax.bts.model.system.flow.FlowRouter

@RunWith(MockitoJUnitRunner::class)
class PhotoDetailPresenterTest {

    @Mock
    lateinit var view: PhotoDetailView

    @Mock
    lateinit var viewState: `PhotoDetailView$$State`

    @Mock
    lateinit var router: FlowRouter

    private lateinit var presenter: PhotoDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val entity = PhotoEntity("", "", "", "", "")
        presenter = spy(PhotoDetailPresenter(entity, router).apply
        {
            setViewState(this@PhotoDetailPresenterTest.viewState)
            attachView(view)
        })
    }

    @Test
    fun testOnFirstViewAttach() {
        verify(viewState).showTitle(any())
        verify(viewState).showPhoto(any())
    }

    @Test
    fun testBackPressed() {
        presenter.onBackPressed()
        verify(router).back()
    }
}