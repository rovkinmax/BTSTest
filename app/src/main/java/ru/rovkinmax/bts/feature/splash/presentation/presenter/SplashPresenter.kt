package ru.rovkinmax.bts.feature.splash.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.rovkinmax.bts.Screens
import ru.rovkinmax.bts.feature.global.presentation.RxError
import ru.rovkinmax.bts.feature.global.presentation.presenter.Presenter
import ru.rovkinmax.bts.feature.splash.presentation.view.SplashView
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import ru.rovkinmax.bts.model.system.rx.SchedulersProvider
import ru.rovkinmax.bts.model.system.rx.subscribe
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(private val router: FlowRouter,
                                          private val schedulersProvider: SchedulersProvider) : Presenter<SplashView>(router) {

    companion object {

        private const val TIME_LOADING = 1000L
    }

    override fun onFirstViewAttach() {
        Observable.timer(TIME_LOADING, TimeUnit.MILLISECONDS, schedulersProvider.computation())
                .observeOn(schedulersProvider.main())
                .compose(lifecycle())
                .subscribe({ router.startNewRootFlow(Screens.FLOW_PHOTOS) }, RxError.doNothing())
    }

    override fun onBackPressed() {}
}