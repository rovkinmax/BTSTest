package ru.rovkinmax.bts.feature.splash.presenter

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.rovkinmax.bts.Screens
import ru.rovkinmax.bts.feature.splash.presentation.presenter.SplashPresenter
import ru.rovkinmax.bts.feature.splash.presentation.view.SplashView
import ru.rovkinmax.bts.feature.splash.presentation.view.`SplashView$$State`
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import ru.rovkinmax.bts.model.system.rx.TestSchedulers

@RunWith(MockitoJUnitRunner::class)
class SplashPresenterTest {

    @Mock
    lateinit var router: FlowRouter

    @Mock
    lateinit var view: SplashView

    @Mock
    lateinit var viewStateSplash: `SplashView$$State`

    private lateinit var presenter: SplashPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = spy(SplashPresenter(router, TestSchedulers()).apply {
            setViewState(viewStateSplash)
            attachView(view)
        })
    }

    @Test
    fun testTestSplashNavigation() {
        verify(router).startNewRootFlow(Screens.FLOW_PHOTOS)
        verifyZeroInteractions(view)
    }

    @Test
    fun testBackNavigation() {
        Mockito.clearInvocations(router)
        presenter.onBackPressed()
        verifyZeroInteractions(router)
        verifyZeroInteractions(view)
    }
}