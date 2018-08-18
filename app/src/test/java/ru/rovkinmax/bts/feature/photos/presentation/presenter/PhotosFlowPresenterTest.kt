package ru.rovkinmax.bts.feature.photos.presentation.presenter

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.rovkinmax.bts.Screens
import ru.rovkinmax.bts.feature.photos.presentation.view.PhotosFlowView
import ru.rovkinmax.bts.feature.photos.presentation.view.`PhotosFlowView$$State`
import ru.rovkinmax.bts.model.system.flow.FlowRouter

@RunWith(MockitoJUnitRunner::class)
class PhotosFlowPresenterTest {
    @Mock
    lateinit var router: FlowRouter
    @Mock
    lateinit var view: PhotosFlowView
    @Mock
    lateinit var flowViewState: `PhotosFlowView$$State`

    private lateinit var presenter: PhotosFlowPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = spy(PhotosFlowPresenter(router).apply {
            setViewState(flowViewState)
            attachView(view)
        })
    }

    @Test
    fun testLaunchScreen() {
        verify(router).newRootScreen(Screens.SCREEN_PHOTO_SEARCH)
        verifyZeroInteractions(view)
    }
}