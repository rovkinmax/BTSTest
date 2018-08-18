package ru.rovkinmax.bts.feature.global.presentation.presenter

import android.support.annotation.VisibleForTesting
import com.arellomobile.mvp.MvpPresenter
import ru.rovkinmax.bts.feature.global.presentation.view.BaseView
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import ru.rovkinmax.bts.model.ui.rx.LifecycleProvider
import ru.rovkinmax.bts.model.ui.rx.LifecycleTransformer

abstract class Presenter<V : BaseView>(private val router: FlowRouter) : MvpPresenter<V>() {

    private val provider = LifecycleProvider()
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun <T> lifecycle(): LifecycleTransformer<T, T> = provider.lifecycle()

    override fun onDestroy() {
        provider.unsubscribe()
        super.onDestroy()
    }

    open fun onBackPressed() {
        router.back()
    }
}